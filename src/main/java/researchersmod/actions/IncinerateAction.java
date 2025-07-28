package researchersmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import researchersmod.Researchers;
import researchersmod.util.Wiz;

public class IncinerateAction
        extends AbstractGameAction {
    private AbstractCard card;

    public IncinerateAction(AbstractCard card) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = AbstractGameAction.ActionType.WAIT;
        this.card = card;
    }


    public void update() {
        int i = 0;
        for (AbstractCard c : DrawCardAction.drawnCards) {
            if (c.hasTag(Researchers.PHASE)) {
                Wiz.att(new ExhaustSpecificCardAction(c,Wiz.p().hand));
                card.baseMagicNumber++;
                i++;
            }
        }
        this.isDone = true;
    }
}

