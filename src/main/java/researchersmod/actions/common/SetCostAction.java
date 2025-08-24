package researchersmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class SetCostAction extends AbstractGameAction {
    private final int cost;
    private final AbstractCard card;
    private final boolean isCostModified;
    public SetCostAction(AbstractCard card, int cost, boolean isCostModified) {
        this.card = card;
        this.cost = cost;
        this.isCostModified = isCostModified;
    }

    public void update() {
        card.cost = cost;
        card.costForTurn = cost;
        card.isCostModified = this.isCostModified;
        this.isDone = true;
    }
}
