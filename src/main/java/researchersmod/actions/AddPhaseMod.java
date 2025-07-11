package researchersmod.actions;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import researchersmod.cardmods.PhaseMod;

public class AddPhaseMod extends AbstractGameAction{
    private final AbstractCard card;

    public AddPhaseMod(AbstractCard card){
        this.actionType = ActionType.CARD_MANIPULATION;
        this.card = card;

    }
    public void update() {
        CardModifierManager.addModifier(card, new PhaseMod());
        this.isDone = true;
    }
}
