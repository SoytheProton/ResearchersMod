package researchersmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import researchersmod.Researchers;
import researchersmod.powers.ManipulationPower;
import researchersmod.util.Wiz;

public class HadalJetsuitAction extends AbstractGameAction {

    public HadalJetsuitAction(int amount) {
        this.actionType = AbstractGameAction.ActionType.WAIT;
        this.amount = amount;
    }
    @Override
    public void update() {
        if(Researchers.cardsPhasedThisCombat % 3 == 0) {
            Wiz.applyToSelf(new ManipulationPower(Wiz.p(),this.amount));
        }
        this.isDone = true;
    }
}
