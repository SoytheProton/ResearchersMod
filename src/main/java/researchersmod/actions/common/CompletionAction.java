package researchersmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.ui.ExperimentCardManager;

public class CompletionAction extends AbstractGameAction {
    private final AbstractPower po;
    public CompletionAction(AbstractPower po) {
        this(po,false);
    }

    public CompletionAction(AbstractPower po, boolean duration) {
        this.po = po;
        if(duration) this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        if(po.amount > 0 && duration == Settings.ACTION_DUR_XFAST) ExperimentCardManager.complete(po);
        tickDuration();
    }
}
