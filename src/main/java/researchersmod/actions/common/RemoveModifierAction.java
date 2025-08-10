package researchersmod.actions.common;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import researchersmod.util.Wiz;

public class RemoveModifierAction extends AbstractGameAction {
    private AbstractCard card;
    private String modifierID;

    public RemoveModifierAction(AbstractCard card, AbstractCardModifier modifier) {
        this.card = card;
        this.modifierID = modifier.identifier(card);
    }

    public RemoveModifierAction(AbstractCard card, String modifierID) {
        this.card = card;
        this.modifierID = modifierID;
    }

    public void update() {
        CardModifierManager.removeModifiersById(card,modifierID,true);
        for(AbstractCard c : Wiz.p().masterDeck.group) if(c.uuid == card.uuid) CardModifierManager.removeModifiersById(c,modifierID,true);
        isDone = true;
    }

}
