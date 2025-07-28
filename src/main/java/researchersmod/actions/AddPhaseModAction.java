package researchersmod.actions;

import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import researchersmod.cardmods.PhaseMod;

public class AddPhaseModAction extends AbstractGameAction{
    private final AbstractCard card;
    private final boolean flash;
    public AddPhaseModAction(AbstractCard card, boolean flash) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.card = card;
        this.flash = flash;
    }

    public AddPhaseModAction(AbstractCard card) {
        this(card,false);
    }
    public void update() {
        CardModifierManager.addModifier(card, new PhaseMod());
        if(flash) card.flash(Color.BLUE.cpy());
        this.isDone = true;
    }
}
