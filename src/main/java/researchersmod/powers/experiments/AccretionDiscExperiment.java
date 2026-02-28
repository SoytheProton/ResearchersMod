package researchersmod.powers.experiments;

import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.fields.ExperimentPowerFields;
import researchersmod.powers.BasePower;
import researchersmod.powers.interfaces.ExperimentInterfaces;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.util.Wiz;

public class AccretionDiscExperiment extends BasePower implements InvisiblePower, NonStackablePower, ExperimentPower, ExperimentInterfaces.OnExperimentInterface {

    public static final String POWER_ID = Researchers.makeID(AccretionDiscExperiment.class.getSimpleName());
    public static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURNBASED = false;
    public AccretionDiscExperiment(AbstractCreature owner, int amount, AbstractCard card) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount,card);
    }
    public void terminateEffect(){
        ExperimentCardManager.remExp(this,false);
    }

    public void completionEffect(){
        Wiz.atb(new GainBlockAction(owner, expCard().block));
        ExperimentCardManager.tickExperiment(this);
    }

    @Override
    public void onExperiment(AbstractPower power) {
        if(power == this) return;
        if(power instanceof ExperimentInterfaces.OnCompletionInterface) {
            ExperimentPowerFields.doNotComplete.set(power, true);
        }
        ExperimentCardManager.tickExp(power,-1);
        ExperimentCardManager.complete(this);
    }

}
