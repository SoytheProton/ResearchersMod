package researchersmod.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class BorealisAuraParticle extends AbstractGameEffect {
    private float x;
    private float y;
    private float vY;
    private float vX;

    private static final float FADE_IN_TIME = 0.5F;
    private static final float FADE_OUT_TIME = 1.0F;
    private static final float TARGET_ALPHA = 0.6F;
    private float fadeInTimer = FADE_IN_TIME;
    private float fadeOutTimer = FADE_OUT_TIME;
    private float stallTimer = 0.1F;
    private TextureAtlas.AtlasRegion img;

    public BorealisAuraParticle(float x, float y) {
        this.img = ImageMaster.VERTICAL_AURA;
        this.color = new Color(33.0F/255.0F, 1.0F, 188.0F/255.0F, 0.0F);
        randomizeColor(this.color, 0.3F);
        this.x = x + MathUtils.random(-200.0F,0.0F) * Settings.scale;
        this.y = y + MathUtils.random(-150.0f,-50.0F) * Settings.scale;
        this.vX = MathUtils.random(-10.0F,10.0F) * Settings.scale;
        this.vY = MathUtils.random(-20.0F,20.0F) * Settings.scale;
        this.scale = MathUtils.random(0.4f,1.0f) * Settings.scale;
    }

    public void update() {
        if (this.stallTimer > 0.0F) {
            this.stallTimer -= Gdx.graphics.getDeltaTime();
            return;
        }
        this.y += this.vY * Gdx.graphics.getDeltaTime();
        this.x += this.vX * Gdx.graphics.getDeltaTime();
        if (this.fadeInTimer != 0.0F) {
            this.fadeInTimer -= Gdx.graphics.getDeltaTime();
            if (this.fadeInTimer < 0.0F)
                this.fadeInTimer = 0.0F;
            this.color.a = Interpolation.fade.apply(TARGET_ALPHA, 0.0F, this.fadeInTimer / FADE_IN_TIME);
        } else if (this.fadeOutTimer != 0.0F) {
            this.fadeOutTimer -= Gdx.graphics.getDeltaTime();
            if (this.fadeOutTimer < 0.0F)
                this.fadeOutTimer = 0.0F;
            this.color.a = Interpolation.fade.apply(0.0F, TARGET_ALPHA, this.fadeOutTimer / FADE_OUT_TIME);
        } else {
            this.isDone = true;
        }
    }

    private void randomizeColor(Color c, float amt) {
        float r = c.r + MathUtils.random(-amt, amt);
        float g = c.g + MathUtils.random(-amt, amt);
        float b = c.b + MathUtils.random(-amt, amt);
        if (r > 1.0F) {
            r = 1.0F;
        } else if (r < 0.0F) {
            r = 0.0F;
        }
        if (g > 1.0F) {
            g = 1.0F;
        } else if (g < 0.0F) {
            g = 0.0F;
        }
        if (b > 1.0F) {
            b = 1.0F;
        } else if (b < 0.0F) {
            b = 0.0F;
        }
        c.r = r;
        c.g = g;
        c.b = b;
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw((TextureRegion)this.img, this.x, this.y, this.img.packedWidth / 2.0F, this.img.packedHeight / 2.0F, this.img.packedWidth, this.img.packedHeight, this.scale, this.scale, this.rotation);
    }

    public void dispose() {}
}