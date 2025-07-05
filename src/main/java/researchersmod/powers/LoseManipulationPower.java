package researchersmod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.util.Wiz;

public class LoseManipulationPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(LoseManipulationPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURNBASED = true;

    public LoseManipulationPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount);
        updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.amount);
    }

    public void atEndOfTurn(boolean isPlayer) {
        flash();
        Wiz.atb(new ApplyPowerAction(owner,owner,new ManipulationPower(owner, -this.amount), -this.amount));
        Wiz.atb(new RemoveSpecificPowerAction(owner, owner, this));
    }
}
