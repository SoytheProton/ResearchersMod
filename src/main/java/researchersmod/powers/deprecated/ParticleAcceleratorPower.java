package researchersmod.powers.deprecated;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.powers.BasePower;
import researchersmod.util.Wiz;

public class ParticleAcceleratorPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(ParticleAcceleratorPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;

    public ParticleAcceleratorPower(AbstractCreature owner, int amt) {
        super(POWER_ID, TYPE, TURNBASED, owner, amt);
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        flash();
        Wiz.atb(new ApplyPowerAction(this.owner,this.owner,new PhaseDrawPower(this.owner,1)));
        Wiz.atb(new ReducePowerAction(this.owner,this.owner,POWER_ID,1));
    }

    public void updateDescription() {
        String plural = "s";
        if(this.amount == 1)
            plural = "";
        this.description = String.format(DESCRIPTIONS[0],this.amount,plural);
    }
}
