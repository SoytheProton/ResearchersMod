package researchersmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MakeCardTypeFree extends AbstractGameAction {
    private static AbstractCard.CardType cardType;

    public MakeCardTypeFree(AbstractCard.CardType cardType){
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        MakeCardTypeFree.cardType = cardType;

    }
    public void update() {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type == cardType) {
                if (c.costForTurn > 0) {
                    c.costForTurn = 0;
                    c.isCostModifiedForTurn = true;
                }
            }
        }
        isDone = true;
    }
}
