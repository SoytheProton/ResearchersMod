package researchersmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.actions.common.ApplyDistortionPowerToAll;
import researchersmod.powers.interfaces.ExperimentInterfaces;

public class IonSurgePower extends BasePower implements ExperimentInterfaces.OnAnyCompletionInterface {
    public static final String POWER_ID = Researchers.makeID(IonSurgePower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;
    private boolean triggeredThisTurn;

    public IonSurgePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount);
        updateDescription();
        triggeredThisTurn = false;
    }

    @Override
    public void atStartOfTurn() {
        triggeredThisTurn = false;
    }

    @Override
    public void onCompletion(AbstractPower power) {
        if(!triggeredThisTurn) {
            flash();
            triggeredThisTurn = true;
            addToBot(new ApplyDistortionPowerToAll(amount));
        }
        updateDescription();
    }
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.amount);
    }
}
