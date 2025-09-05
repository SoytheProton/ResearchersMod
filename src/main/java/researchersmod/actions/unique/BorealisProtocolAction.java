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
import researchersmod.vfx.BorealisAuraParticle;

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
                for(int y = 0; y<6; y++) AbstractDungeon.topLevelEffectsQueue.add(new BorealisAuraParticle(c.current_x,c.current_y));
            }
        }
        this.isDone = true;
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
