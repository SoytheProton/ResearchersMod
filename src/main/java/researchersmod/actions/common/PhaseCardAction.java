package researchersmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import researchersmod.cardmods.PhaseMod;

public class PhaseCardAction extends AbstractGameAction {
    private AbstractCard targetCard;

    public PhaseCardAction(AbstractCard targetCard) {
        this.targetCard = targetCard;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            new PhaseMod().onExhausted(targetCard);
        }
        this.isDone = true;
    }
}
