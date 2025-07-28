package researchersmod.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import researchersmod.Researchers;
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
        if (!tmpGroup.isEmpty()) for(AbstractCard c : tmpGroup.group) {
            if(c.hasTag(Researchers.PHASE)) Wiz.att(new ExhaustSpecificCardAction(c,Wiz.p().drawPile,true));
            else Wiz.att(new DiscardSpecificCardAction(c,Wiz.p().drawPile));
        }
        this.isDone = true;
    }
}
