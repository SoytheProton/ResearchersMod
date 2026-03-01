package researchersmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class HadalJetsuitAction extends AbstractGameAction {

    public HadalJetsuitAction(int amount) {
        this.duration = 0.0F;
        this.actionType = ActionType.WAIT;
        this.amount = amount;
    }

        public void update() {
            for (AbstractCard c : DrawCardAction.drawnCards) {
                if (c.type == AbstractCard.CardType.STATUS) {
                    AbstractDungeon.actionManager.addToTop(new DrawCardAction(this.amount));
                    break;
                }
            }

            this.isDone = true;
        }
}
