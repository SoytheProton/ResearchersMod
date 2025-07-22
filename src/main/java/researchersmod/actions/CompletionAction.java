package researchersmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.powers.interfaces.ExperimentPower;

public class CompletionAction extends AbstractGameAction {
    private final AbstractPlayer p;
    private final AbstractPower po;
    public CompletionAction(AbstractPlayer p, AbstractPower po) {
        this.p = p;
        this.po = po;
    }

    public void update() {
        if(po.amount > 0) ((ExperimentPower) po).completionEffect();
    }
}
