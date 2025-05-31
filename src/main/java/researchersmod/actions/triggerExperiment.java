package researchersmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.util.ExperimentUtil;
import researchersmod.util.Wiz;

public class triggerExperiment extends AbstractGameAction {

    private AbstractPower power;
    public triggerExperiment(AbstractPower p){
        this.actionType = ActionType.SPECIAL;
        power = p;
    }

    public void update(){
        for(AbstractPower p : Wiz.adp().powers){
            if(p instanceof ExperimentUtil.onExperimentInterface){
                ((ExperimentUtil.onExperimentInterface) p).onExperiment(power);
            }
        }
        isDone = true;
    }
}
