package researchersmod.powers.experiments;

import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.powers.BasePower;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.powers.interfaces.ExperimentInterfaces;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.util.Wiz;

import java.util.Objects;

public class ShortCircuitExperiment extends BasePower implements InvisiblePower, NonStackablePower, ExperimentPower, ExperimentInterfaces.onCompletionInterface {

    public static final String POWER_ID = Researchers.makeID(ShortCircuitExperiment.class.getSimpleName());
    public static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURNBASED = false;
    public ShortCircuitExperiment(AbstractCreature owner, int amount, AbstractCard card) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount,card);
    }

    public void terminateEffect(){
        ExperimentCardManager.remExp(k,this,true);
    }

    public void completionEffect(){
        Wiz.atb(new LoseEnergyAction(1));
        ExperimentCardManager.tickExperiment(this);
    }

    @Override
    public void onCompletion(AbstractPower power) {
        if(!Objects.equals(power.ID, POWER_ID)) {
            completionEffect();
        }
    }

}
