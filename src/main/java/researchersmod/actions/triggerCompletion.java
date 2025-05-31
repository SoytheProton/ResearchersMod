package researchersmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.util.ExperimentUtil;
import researchersmod.util.Wiz;

public class triggerCompletion  extends AbstractGameAction {

    private AbstractPower power;
    public triggerCompletion(AbstractPower p){
        this.actionType = ActionType.SPECIAL;
        power = p;
    }

    public void update(){
        for(AbstractPower p : Wiz.adp().powers){
            if(p instanceof ExperimentUtil.onCompletionInterface){
                ((ExperimentUtil.onCompletionInterface) p).onCompletion(power);
            }
        }
        isDone = true;
    }
}
