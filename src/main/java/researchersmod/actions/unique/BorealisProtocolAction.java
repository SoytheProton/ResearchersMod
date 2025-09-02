package researchersmod.actions.unique;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.helpers.VfxBuilder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import researchersmod.cardmods.PhaseMod;

public class BorealisProtocolAction extends AbstractGameAction {
    private final AbstractPlayer owner;
    private final int amount;

    public BorealisProtocolAction(AbstractPlayer owner, int amount) {
        this.owner = owner;
        this.amount = amount;
    }
    @Override
    public void update() {
        for(AbstractCard c : ((AbstractPlayer) owner).hand.group) {
            if(CardModifierManager.hasModifier(c, PhaseMod.ID) && c.type != AbstractCard.CardType.POWER) {
                AbstractCardModifier mod = CardModifierManager.getModifiers(c, PhaseMod.ID).get(0);
                for(int i = this.amount; i>0; i--) {
                    mod.onExhausted(c);
                }
                c.flash(Color.BLUE.cpy());
                for(int y = 0; y<6; y++) AbstractDungeon.topLevelEffectsQueue.add(borealisAura(c.current_x,c.current_y));
            }
        }
        this.isDone = true;
    }

    private static AbstractGameEffect borealisAura(float x, float y) {
        Color auraColor = new Color(33.0F/255.0F, 1.0F, 188.0F/255.0F, 0.5F);
        float randomX = x+MathUtils.random(-200.0F,200F);
        randomizeColor(auraColor,0.3F);
        return new VfxBuilder(ImageMaster.VERTICAL_AURA,x,y,1.5f)
                .setColor(auraColor)
                .setAlpha(0.6f)
                .setScale(MathUtils.random(0.4f,1.0f))
                .fadeIn(0.9f)
                .fadeOutFromOriginalAlpha(0.6f)
                .moveY(y+MathUtils.random(-100.0f,100.0F)*Settings.scale,y+MathUtils.random(-75.0F,75.0F) * Settings.scale, VfxBuilder.Interpolations.CIRCLEOUT)
                .moveX(randomX+MathUtils.random(-20.0f,20.0F)* Settings.scale,randomX+MathUtils.random(-20.0F,20.0F) * Settings.scale, VfxBuilder.Interpolations.CIRCLEOUT)
                .build();
    }

    private static void randomizeColor(Color c, float amt) {
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
}
