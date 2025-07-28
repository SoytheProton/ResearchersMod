package researchersmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import researchersmod.util.Wiz;

public class DelayAction extends AbstractGameAction {
    private final AbstractGameAction action;
    public DelayAction(AbstractGameAction action) {
        this.action = action;
    }

    public void update() {
        Wiz.atb(action);
        this.isDone = true;
    }
}
