package researchersmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.actions.common.CompletionAction;
import researchersmod.util.Wiz;

public class CompleteAgainAction extends AbstractGameAction {
    private final AbstractPower po;
    public CompleteAgainAction(AbstractPower po) {
        this.po = po;
    }

    public void update() {
        if(po.amount > 0) {
            Wiz.att(new CompleteAgainAction(po));
            Wiz.att(new CompletionAction(po,true));
        }
        this.isDone = true;
    }
}