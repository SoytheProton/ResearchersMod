package researchersmod.actions;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import researchersmod.cardmods.BetterEtherealMod;

public class AddBetterEtherealMod extends AbstractGameAction{
    private final AbstractCard card;

    public AddBetterEtherealMod(AbstractCard card){
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.card = card;

    }
    public void update() {
        CardModifierManager.addModifier(card, new BetterEtherealMod());
        this.isDone = true;
    }
}
