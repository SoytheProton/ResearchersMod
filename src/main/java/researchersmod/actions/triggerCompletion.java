package researchersmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.powers.BasePower;
import researchersmod.util.ExpUtil;
import researchersmod.util.Wiz;

public class triggerCompletion  extends AbstractGameAction {

    private AbstractPower power;
    public triggerCompletion(AbstractPower p){
        this.actionType = ActionType.SPECIAL;
        power = p;
        for(AbstractPower pow : Wiz.adp().powers){
            if(pow instanceof ExpUtil.onCompletionInterface && ((BasePower) pow).PriorityActivation){
                ((ExpUtil.onCompletionInterface) pow).onCompletion(power);
            }
        }
    }

    public void update(){
        for(AbstractPower p : Wiz.adp().powers){
            if(p instanceof ExpUtil.onCompletionInterface && !((BasePower) p).PriorityActivation){
                ((ExpUtil.onCompletionInterface) p).onCompletion(power);
            }
        }
        isDone = true;
    }
}
