package researchersmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.util.ExpUtil;
import researchersmod.util.Wiz;

public class triggerTerminate extends AbstractGameAction {

    private AbstractPower power;
    public triggerTerminate(AbstractPower p){
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        power = p;
    }

    public void update(){
        for(AbstractPower p : Wiz.adp().powers){
            if(p instanceof ExpUtil.onTerminateInterface){
                ((ExpUtil.onTerminateInterface) p).onTerminate(power);
            }
        }
        isDone = true;
    }
}
