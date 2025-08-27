package researchersmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.powers.interfaces.ExperimentPower;

public class TerminateAction extends AbstractGameAction {
    private final AbstractPower po;
    public TerminateAction(AbstractPower po) {
        this.po = po;
    }

    public void update() {
        ((ExperimentPower) po).terminateEffect();
        this.isDone = true;
    }
}
