package researchersmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.actions.common.DelayAction;
import researchersmod.actions.unique.FuckAssAccelechargerAction;
import researchersmod.util.Wiz;

public class AccelechargerPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(AccelechargerPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;

    public AccelechargerPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURNBASED, owner, 1);
        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        String plural = "s";
        if(this.amount == 1) plural = "";
        Wiz.atb(new DelayAction(new FuckAssAccelechargerAction(DESCRIPTIONS[1],plural,this.amount)));
    }

    public void updateDescription() {
        String plural = "s";
        if(this.amount == 1) plural = "";
        this.description = String.format(DESCRIPTIONS[0],this.amount,plural);
    }
}
