package researchersmod.powers.experiments;

import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import researchersmod.Researchers;
import researchersmod.powers.BasePower;
import researchersmod.powers.interfaces.ExperimentInterfaces;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.util.Wiz;

import java.util.Objects;

public class DigithunderExperiment extends BasePower implements InvisiblePower, NonStackablePower, ExperimentPower, ExperimentInterfaces.OnTerminateInterface {

    public static final String POWER_ID = Researchers.makeID(DigithunderExperiment.class.getSimpleName());
    public static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURNBASED = false;

    public DigithunderExperiment(AbstractCreature owner, int amount, AbstractCard card) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount,card);
    }

    public void terminateEffect(){
        ExperimentCardManager.remExp(this,true);
    }

    public void completionEffect(){
        Wiz.applyToSelf(new StrengthPower(owner, expCard().magicNumber));
        ExperimentCardManager.tickExperiment(this);
    }

    @Override
    public void onTerminate(AbstractPower power) {
        if(!Objects.equals(power.ID, POWER_ID)) {
            ExperimentCardManager.complete(this);
        }
    }
}
