package researchersmod.powers.deprecated;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.powers.BasePower;
import researchersmod.powers.interfaces.ExperimentInterfaces;
@SuppressWarnings("all")
public class InfluxPowerPower extends BasePower implements ExperimentInterfaces.OnCompletionInterface {
    public static final String POWER_ID = Researchers.makeID(InfluxPowerPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;

    public InfluxPowerPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount);
        updateDescription();
    }
    @Override
    public void onCompletion(AbstractPower p) {
        flash();
        addToBot(new GainBlockAction(this.owner, this.owner, this.amount));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
