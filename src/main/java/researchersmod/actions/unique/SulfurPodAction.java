package researchersmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import researchersmod.cards.status.SulfurPod;
import researchersmod.util.Wiz;

import java.util.Objects;

public class SulfurPodAction extends AbstractGameAction {
    @Override
    public void update() {
        for(AbstractCard c : Wiz.p().hand.group)
            if(Objects.equals(c.cardID, SulfurPod.ID))
                Wiz.atb(new ExhaustSpecificCardAction(c,Wiz.p().hand));
        isDone = true;
    }
}
