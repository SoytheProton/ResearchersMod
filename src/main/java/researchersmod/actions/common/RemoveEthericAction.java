package researchersmod.actions.common;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import researchersmod.cardmods.EthericMod;

public class RemoveEthericAction extends AbstractGameAction {
    private AbstractCard card;
    private String rawDescription;
    public RemoveEthericAction(AbstractCard card, String rawDescription) {
        this.card = card;
        this.rawDescription = rawDescription;
        this.duration = 0F;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
    }
    @Override
    public void update() {
        CardModifierManager.removeModifiersById(card, EthericMod.ID,true);
        card.isEthereal = true;
        this.isDone = true;
    }
}
