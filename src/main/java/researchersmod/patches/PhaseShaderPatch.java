package researchersmod.patches;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import com.megacrit.cardcrawl.vfx.combat.DamageImpactCurvyEffect;
import researchersmod.Researchers;
import researchersmod.patches.occultpatchesthatliterallyexistonlyforphasetobeplayablewhileunplayable.PhasingFields;

import java.util.logging.Logger;

// shamelessly stolen from Broken Space. I do not know shaders. It hurts my brain.
@SuppressWarnings("unused")
public class PhaseShaderPatch {
    public static ShaderProgram phaseShader;
    private static final FrameBuffer fbo;

    private static final Logger logger = Logger.getLogger(PhaseShaderPatch.class.getName());

    @SpirePatch(
            clz = AbstractCard.class,
            method = "renderCard"
    )
    public static class RenderPhaseCards {
        @SpirePrefixPatch
        public static void addShader(AbstractCard __instance, SpriteBatch sb) {
            if (shouldRenderPhaseShader(__instance)) {
                StartFbo(sb);
            }
        }

        @SpirePostfixPatch
        public static void removeShader(AbstractCard __instance, SpriteBatch sb) {
            if (shouldRenderPhaseShader(__instance)) {
                float strength = 1.0F;

                if (__instance.hb.hovered) {
                    strength = 0.2F;
                }
                StopFbo(sb, strength, RandomTimeField.randomTime.get(__instance));

            }
        }
    }


    public static void StartFbo(SpriteBatch sb) {
        sb.flush();
        fbo.begin();

        Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    public static void StopFbo(SpriteBatch sb) {
        StopFbo(sb, 1.0F);
    }

    public static void StopFbo(SpriteBatch sb, float strength) {
        StopFbo(sb, strength, 0.0F);
    }

    private static void StopFbo(SpriteBatch sb, float strength, float timerOffset) {
        StopFbo(sb, strength, timerOffset, 0.03F);
    }

    private static void StopFbo(SpriteBatch sb, float strength, float timerOffset, float chrAb) {
        StopFbo(sb, strength, timerOffset, chrAb, 1.0F);
    }

    public static void StopFbo(SpriteBatch sb, float strength, float timerOffset, float chrAb, float timeScale) {
        StopFbo(sb, strength, timerOffset, chrAb, timeScale, 1f);
    }

    public static void StopFbo(SpriteBatch sb, float strength, float timerOffset, float chrAb, float timeScale, float UVScl) {

        sb.flush();
        fbo.end();


        TextureRegion region = new TextureRegion(fbo.getColorBufferTexture());
        region.flip(false, true);


        sb.setShader(phaseShader);
        sb.setColor(Color.WHITE);
        phaseShader.setUniformf("u_time", Gdx.graphics.getDeltaTime() * timeScale + timerOffset);
        phaseShader.setUniformf("u_strength", strength);
        phaseShader.setUniformf("u_chrAb", chrAb);
        phaseShader.setUniformf("u_UVScl", UVScl);

        sb.draw(region, 0, 0);
        sb.setShader(null);
        sb.flush();
    }

    private static boolean shouldRenderPhaseShader(AbstractCard __instance) {
        return PhasingFields.isPhasing.get(__instance);
    }


    @SpirePatch2(
            clz = AbstractCard.class,
            method = SpirePatch.CLASS
    )
    public static class RandomTimeField {
        public static SpireField<Float> randomTime = new SpireField<>(() -> (float) Math.random() * 1000f);
    }

    @SpirePatch2(
            clz = ExhaustCardEffect.class,
            method = "update"
    )
    public static class removeExhaustParticles {
        @SpirePrefixPatch
        public static void Prefix(AbstractCard ___c, @ByRef float[] ___duration) {
            if(___duration[0] == 1.0F && PhasingFields.isPhasing.get(___c)) {
                ___duration[0] -= Gdx.graphics.getDeltaTime();
                int i;
                for (i = 0; i < 16; i++)
                    AbstractDungeon.topLevelEffectsQueue.add(new DamageImpactCurvyEffect(___c.current_x, ___c.current_y, new Color(0.2F, 0.15F, 0.2F, 0.01F), false));
                for (i = 0; i < 8; i++)
                    AbstractDungeon.effectsQueue.add(new DamageImpactCurvyEffect(___c.current_x, ___c.current_y, new Color(0.6F, 0.6F, 0.6F, 0.01F), false));
            }
        }
    }


    static {
        phaseShader = new ShaderProgram(Gdx.files.internal(Researchers.imagePath("shaders/Phase.vs")), Gdx.files.internal(Researchers.imagePath("shaders/Phase.fs")));
        if (!phaseShader.isCompiled()) {
            logger.warning("Phase shader: " + phaseShader.getLog());
        }
        phaseShader.begin();


        fbo = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, false);
    }
}
