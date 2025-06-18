package researchersmod.powers.experiments;

import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.powers.BasePower;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.util.ExperimentPower;
import researchersmod.util.ExpUtil;
import researchersmod.util.Wiz;

import java.util.Objects;

public class FolderExperiment extends BasePower implements InvisiblePower, NonStackablePower, ExperimentPower, ExpUtil.onCompletionInterface {

    public static final String POWER_ID = Researchers.makeID(FolderExperiment.class.getSimpleName());
    public static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURNBASED = false;


    public FolderExperiment(AbstractCreature owner, int amount, AbstractCard card) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount,card);
    }

    public void terminateEffect(){
        ExperimentCardManager.remExp(k,true);
        Wiz.att(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    public void completionEffect(){
        ExperimentCardManager.tickExperiment(this);
    }

    @Override
    public void onCompletion(AbstractPower power) {
        if(!Objects.equals(power.ID, POWER_ID)) {
            ExperimentCardManager.tickExperiment(power,-1);
            completionEffect();
            if (this.amount <= 0) {
                terminateEffect();
            }
        }
    }
}
