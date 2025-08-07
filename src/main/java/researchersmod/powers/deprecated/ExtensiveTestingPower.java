package researchersmod.powers.deprecated;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.powers.BasePower;
import researchersmod.powers.interfaces.ExperimentInterfaces;
import researchersmod.ui.ExperimentCardManager;
@SuppressWarnings("all")
public class ExtensiveTestingPower extends BasePower implements ExperimentInterfaces.OnExperimentInterface {
    public static final String POWER_ID = Researchers.makeID(ExtensiveTestingPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;

    public ExtensiveTestingPower(AbstractCreature owner, int amt) {
        super(POWER_ID, TYPE, TURNBASED, owner, amt);
        updateDescription();
    }

    public void onExperiment(AbstractPower power) {
        flash();
        ExperimentCardManager.tickExp(power,-this.amount);
    }

    public void updateDescription() {
        String plural = "s";
        if(this.amount == 1) plural = "";
        this.description = String.format(DESCRIPTIONS[0],this.amount,plural);
    }
}
