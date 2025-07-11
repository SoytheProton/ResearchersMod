package researchersmod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import researchersmod.Researchers;
import researchersmod.util.Wiz;

public class O5CommandPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(O5CommandPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;

    public O5CommandPower(AbstractCreature owner, int amt) {
        super(POWER_ID, TYPE, TURNBASED, owner, amt);
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        flash();
        Wiz.att(new ApplyPowerAction(this.owner,this.owner,new NoDrawPower(this.owner)));
        Wiz.atb(new ReducePowerAction(this.owner,this.owner,POWER_ID,1));
    }

    public void updateDescription() {
        String plural = "s";
        if(this.amount == 1) plural = "";
        this.description = String.format(DESCRIPTIONS[0],this.amount,plural);
    }
}
