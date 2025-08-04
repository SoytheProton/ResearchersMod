package researchersmod.powers;

import com.megacrit.cardcrawl.actions.common.EndTurnAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.util.Wiz;

public class InstabilityPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(InstabilityPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;

    public InstabilityPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount);
        updateDescription();
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if(this.amount >= 3) {
            Wiz.att(new RemoveSpecificPowerAction(owner, owner, this));
            Wiz.att(new EndTurnAction());
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
