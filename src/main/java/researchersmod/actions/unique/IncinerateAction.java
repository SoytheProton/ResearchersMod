package researchersmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import researchersmod.util.Wiz;

public class IncinerateAction
        extends AbstractGameAction {
    private final AbstractPlayer p;
    public IncinerateAction(AbstractPlayer p) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = AbstractGameAction.ActionType.WAIT;
        this.p = p;
    }


    public void update() {
        int i = 0;
        for(AbstractCard c : p.hand.group) {
            if(c.type == AbstractCard.CardType.STATUS) {
                i++;
                Wiz.att(new ExhaustSpecificCardAction(c,p.hand,true));
            }
        }
        Wiz.atb(new DrawCardAction(i));
        this.isDone = true;
    }
}

