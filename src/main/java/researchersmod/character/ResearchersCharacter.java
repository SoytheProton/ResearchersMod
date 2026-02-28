package researchersmod.character;

import basemod.BaseMod;
import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import basemod.animations.AbstractAnimation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.*;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import researchersmod.cards.basic.Defend_Researchers;
import researchersmod.cards.basic.Development;
import researchersmod.cards.basic.Research;
import researchersmod.cards.basic.Strike_Researchers;
import researchersmod.relics.DatabaseTablet;

import java.util.ArrayList;
import java.util.List;

import static researchersmod.Researchers.characterPath;
import static researchersmod.Researchers.makeID;

public class ResearchersCharacter extends CustomPlayer {
    private static final String ID = makeID("Researchers");
    private static String[] getNames() { return CardCrawlGame.languagePack.getCharacterString(ID).NAMES; }
    private static String[] getText() { return CardCrawlGame.languagePack.getCharacterString(ID).TEXT; }
    private static final String SHOULDER_1 = characterPath("shoulder.png");
    private static final String SHOULDER_2 = characterPath("shoulder2.png");
    private static final String CORPSE = characterPath("corpse.png");

    //Textures used for the energy orb
    private static final String[] orbTextures = {
            characterPath("energyorb/layer1.png"), //When you have energy
            characterPath("energyorb/layer2.png"),
            characterPath("energyorb/layer3.png"),
            characterPath("energyorb/layer4.png"),
            characterPath("energyorb/layer5.png"),
            characterPath("energyorb/cover.png"), //"container"
            characterPath("energyorb/layer1d.png"), //When you don't have energy
            characterPath("energyorb/layer2d.png"),
            characterPath("energyorb/layer3d.png"),
            characterPath("energyorb/layer4d.png"),
            characterPath("energyorb/layer5d.png")
    };

    private static final float[] layerSpeeds = new float[] {
            -20.0F,
            20.0F,
            -40.0F,
            40.0F,
            360.0F
    };


    protected TextureAtlas prootAtlas = null;
    protected Skeleton prootSkeleton;
    public AnimationState prootState;
    protected AnimationStateData prootStateData;

    public float prootDrawX;
    public float prootDrawY;

    protected TextureAtlas mothAtlas = null;
    protected Skeleton mothSkeleton;
    public AnimationState mothState;
    protected AnimationStateData mothStateData;

    public float mothDrawX;
    public float mothDrawY;

    private final static float OFFSET_X = 70.0F;

    @Override
    public void dispose() {
        super.dispose();

        if(this.mothAtlas != null) mothAtlas.dispose();

        if(prootAtlas != null) prootAtlas.dispose();
    }

    public ResearchersCharacter() {
        super(getNames()[0], Meta.RESEARCHERS,
                new CustomEnergyOrb(orbTextures, characterPath("energyorb/vfx.png"), layerSpeeds), //Energy Orb
                new AbstractAnimation() {
                    @Override
                    public Type type() {
                        return Type.NONE;
                    }
                });

        this.mothDrawX = drawX - OFFSET_X * Settings.scale * 0.8F;
        this.mothDrawY = drawY;

        this.prootDrawX = drawX + OFFSET_X * Settings.scale;
        this.prootDrawY = drawY;

        initializeClass(null,
                SHOULDER_2,
                SHOULDER_1,
                CORPSE,
                getLoadout(),
                20.0F, -20.0F, 200.0F, 250.0F, //Character hitbox. x y position, then width and height.
                new EnergyManager(3));

        this.atlas = new TextureAtlas(Gdx.files.internal(characterPath("animation/polarisskeleton.atlas")));

        loadProotAnimation();
        loadMothAnimation();
        dialogX = (drawX + OFFSET_X * Settings.scale);
        dialogY = (drawY + 220.0F * Settings.scale);
    }

    private void loadProotAnimation() {
        this.prootAtlas = new TextureAtlas(Gdx.files.internal(characterPath("animation/polarisskeleton.atlas")));
        SkeletonJson json = new SkeletonJson(this.prootAtlas);
        json.setScale(Settings.scale/4.5F);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(characterPath("animation/polarisskeleton.json")));
        this.prootSkeleton = new Skeleton(skeletonData);
        this.prootSkeleton.setColor(Color.WHITE);
        this.prootStateData = new AnimationStateData(skeletonData);
        this.prootState = new AnimationState(this.prootStateData);
        this.prootStateData.setMix("Hit", "Idle", 0.1F);
        this.prootState.setAnimation(0, "Idle", true);
    }

    private void loadMothAnimation() {
        this.mothAtlas = new TextureAtlas(Gdx.files.internal(characterPath("animation/auroraskeleton.atlas")));
        SkeletonJson json = new SkeletonJson(this.mothAtlas);
        json.setScale(Settings.scale/4.5F);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(characterPath("animation/auroraskeleton.json")));
        this.mothSkeleton = new Skeleton(skeletonData);
        this.mothSkeleton.setColor(Color.WHITE);
        this.mothStateData = new AnimationStateData(skeletonData);
        this.mothState = new AnimationState(this.mothStateData);
        this.mothStateData.setMix("Hit", "Idle", 0.1F);
        this.mothState.setAnimation(0, "Idle", true);
    }

    public void movePosition(float x, float y) {
        float dialogOffsetX = this.dialogX - this.drawX;
        float dialogOffsetY = this.dialogY - this.drawY;
        this.drawX = x;
        this.drawY = y;
        this.mothDrawX = x - OFFSET_X * Settings.scale * 0.8F;
        this.mothDrawY = y;
        this.prootDrawX = x + OFFSET_X * Settings.scale;
        this.prootDrawY = y;
        this.dialogX = this.drawX + dialogOffsetX;
        this.dialogY = this.drawY + dialogOffsetY;
        this.animX = 0.0F;
        this.animY = 0.0F;
        this.refreshHitboxLocation();
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Strike_Researchers.ID);
        retVal.add(Strike_Researchers.ID);
        retVal.add(Strike_Researchers.ID);
        retVal.add(Strike_Researchers.ID);
        retVal.add(Defend_Researchers.ID);
        retVal.add(Defend_Researchers.ID);
        retVal.add(Defend_Researchers.ID);
        retVal.add(Defend_Researchers.ID);
        retVal.add(Research.ID);
        retVal.add(Development.ID);

        return retVal;
    }

    public void damage(DamageInfo info) {
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output - this.currentBlock > 0) {
            AnimationState.TrackEntry e = this.mothState.setAnimation(0, "Hit", false);
            this.mothState.addAnimation(0, "Idle", true, 0.0F);
            AnimationState.TrackEntry e2 = this.prootState.setAnimation(0, "Hit", false);
            this.prootState.addAnimation(0, "Idle", true, 0.0F);
        }
        super.damage(info);
    }

    public void renderPlayerImage(SpriteBatch sb) {
        this.prootState.update(Gdx.graphics.getDeltaTime());
        this.prootState.apply(this.prootSkeleton);
        this.prootSkeleton.updateWorldTransform();
        this.prootSkeleton.setPosition(this.prootDrawX + this.animX, this.prootDrawY + this.animY);
        this.prootSkeleton.setColor(this.tint.color);
        this.prootSkeleton.setFlip(this.flipHorizontal, this.flipVertical);
        this.mothState.update(Gdx.graphics.getDeltaTime());
        this.mothState.apply(this.mothSkeleton);
        this.mothSkeleton.updateWorldTransform();
        this.mothSkeleton.setPosition(this.mothDrawX + this.animX, this.mothDrawY + this.animY);
        this.mothSkeleton.setColor(this.tint.color);
        this.mothSkeleton.setFlip(this.flipHorizontal, this.flipVertical);
        sb.end();
        CardCrawlGame.psb.begin();
        sr.draw(CardCrawlGame.psb, this.mothSkeleton);
        sr.draw(CardCrawlGame.psb, this.prootSkeleton);
        CardCrawlGame.psb.end();
        sb.begin();
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(DatabaseTablet.ID);
        return retVal;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Research();
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[] {
                AbstractGameAction.AttackEffect.SLASH_VERTICAL,
                AbstractGameAction.AttackEffect.SLASH_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY
        };
    }

    private final Color cardRenderColor = new Color(30f/255f, 39f/255f, 166f/255f, 1f); //Used for some vfx on moving cards (sometimes) (maybe)
    private final Color cardTrailColor = new Color(30f/255f, 39f/255f, 166f/255f, 1f); //Used for card trail vfx during gameplay.
    private final Color slashAttackColor = new Color(30f/255f, 39f/255f, 166f/255f, 1f); //Used for a screen tint effect when you attack the heart.
    @Override
    public Color getCardRenderColor() {
        return cardRenderColor;
    }

    @Override
    public Color getCardTrailColor() {
        return cardTrailColor;
    }

    @Override
    public Color getSlashAttackColor() {
        return slashAttackColor;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        //Font used to display your current energy.
        //energyNumFontRed, Blue, Green, and Purple are used by the basegame characters.
        //It is possible to make your own, but not convenient.
        return FontHelper.energyNumFontRed;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_IRON_3", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_IRON_3";
    }

    @Override
    public String getLocalizedCharacterName() {
        return getNames()[0];
    }
    @Override
    public String getTitle(PlayerClass playerClass) {
        return getNames()[1];
    }
    @Override
    public String getSpireHeartText() {
        return getText()[1];
    }
    @Override
    public String getVampireText() {
        return getText()[2];
    }
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(getNames()[0], getText()[0],
                75, 75, 0, 99, 5, this,
                getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return Meta.CARD_COLOR;
    }

    @Override
    public AbstractPlayer newInstance() {
        return new ResearchersCharacter();
    }

    @Override
    public List<CutscenePanel> getCutscenePanels() {
        ArrayList<CutscenePanel> panels = new ArrayList<>();

        panels.add(new CutscenePanel("researchersmod/images/ending/EndingSlice_1.png", "ATTACK_IRON_3"));
        panels.add(new CutscenePanel("researchersmod/images/ending/EndingSlice_2.png", "ATTACK_MAGIC_BEAM_SHORT"));
        panels.add(new CutscenePanel("researchersmod/images/ending/EndingSlice_3.png"));
        return panels;
    }

    @Override
    public Texture getCutsceneBg() {
        return new Texture("researchersmod/images/ending/ResearchersBg.jpg");
    }

    public static class Meta {
        @SpireEnum
        public static PlayerClass RESEARCHERS;
        @SpireEnum(name = "CHARACTER_DARKBLUE_COLOR")
        public static AbstractCard.CardColor CARD_COLOR;
        @SpireEnum(name = "CHARACTER_DARKBLUE_COLOR") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
        private static final String CHAR_SELECT_BUTTON = characterPath("select/button.png");
        private static final String CHAR_SELECT_PORTRAIT = characterPath("select/portrait.png");
        private static final String BG_ATTACK = characterPath("cardback/bg_attack.png");
        private static final String BG_ATTACK_P = characterPath("cardback/bg_attack_p.png");
        private static final String BG_SKILL = characterPath("cardback/bg_skill.png");
        private static final String BG_SKILL_P = characterPath("cardback/bg_skill_p.png");
        private static final String BG_POWER = characterPath("cardback/bg_power.png");
        private static final String BG_POWER_P = characterPath("cardback/bg_power_p.png");
        private static final String ENERGY_ORB = characterPath("cardback/energy_orb.png");
        private static final String ENERGY_ORB_P = characterPath("cardback/energy_orb_p.png");
        private static final String SMALL_ORB = characterPath("cardback/small_orb.png");
        private static final Color cardColor = new Color(30f/255f, 39f/255f, 166f/255f, 1f);
        public static void registerColor() {
            BaseMod.addColor(CARD_COLOR, cardColor,
                    BG_ATTACK, BG_SKILL, BG_POWER, ENERGY_ORB,
                    BG_ATTACK_P, BG_SKILL_P, BG_POWER_P, ENERGY_ORB_P,
                    SMALL_ORB);
        }

        public static void registerCharacter() {
            BaseMod.addCharacter(new ResearchersCharacter(), CHAR_SELECT_BUTTON, CHAR_SELECT_PORTRAIT);
        }
    }

}
