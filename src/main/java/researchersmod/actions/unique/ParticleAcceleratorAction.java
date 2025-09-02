package researchersmod.actions.unique;


import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import researchersmod.cardmods.PhaseMod;
import researchersmod.util.Wiz;

public class ParticleAcceleratorAction
        extends AbstractGameAction
{
    private float startingDuration;
    private CardGroup tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    public ParticleAcceleratorAction(int numCards) {
        this.amount = numCards;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.startingDuration = Settings.ACTION_DUR_XFAST;
        this.duration = this.startingDuration;
    }


    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
            return;
        }
        if(this.duration == this.startingDuration) {
            if (AbstractDungeon.player.drawPile.isEmpty()) {
                this.isDone = true;
                return;
            }
            if (this.amount != -1) {
                for (int i = 0; i < Math.min(this.amount, AbstractDungeon.player.drawPile.size()); i++) {
                    tmpGroup.addToTop(AbstractDungeon.player.drawPile.group
                            .get(AbstractDungeon.player.drawPile.size() - i - 1));
                }
            } else {
                for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                    tmpGroup.addToBottom(c);
                }
            }
            if (!tmpGroup.isEmpty()) for (AbstractCard c : tmpGroup.group) {
                Wiz.p().drawPile.removeCard(c);
                AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(c));
                if (CardModifierManager.hasModifier(c, PhaseMod.ID)) {
                    AbstractCard tmp = c.makeSameInstanceOf();
                    tmp.purgeOnUse = true;
                    tmp.target_x = Settings.WIDTH / 2.0F;
                    tmp.target_y = Settings.HEIGHT / 2.0F;
                    tmp.current_x = Settings.WIDTH / 2.0F;
                    tmp.current_y = Settings.HEIGHT / 2.0F;
                    Wiz.p().limbo.addToBottom(tmp);
                    Wiz.att(new ExhaustSpecificCardAction(tmp, Wiz.p().limbo));
                }
            }
        }
        tickDuration();
    }
}
