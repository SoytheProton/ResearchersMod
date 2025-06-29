package researchersmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.powers.interfaces.ExperimentInterfaces;
import researchersmod.ui.ExperimentCardManager;

public class ExtensiveTestingPower extends BasePower implements ExperimentInterfaces.onExperimentInterface {
    public static final String POWER_ID = Researchers.makeID(ExtensiveTestingPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;

    public ExtensiveTestingPower(AbstractCreature owner, int amt) {
        super(POWER_ID, TYPE, TURNBASED, owner, amt);
        updateDescription();
    }

    public void onExperiment(AbstractPower power) {
        ExperimentCardManager.tickExp(power,-this.amount);
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.amount) + String.format(DESCRIPTIONS[1],this.amount);
    }
}
