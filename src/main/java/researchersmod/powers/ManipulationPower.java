package researchersmod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;

public class ManipulationPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(ManipulationPower.class.getSimpleName());
    public static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURNBASED = false;

    public ManipulationPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount);
        this.canGoNegative = true;
        updateDescription();
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount == 0)
            addToTop((AbstractGameAction)new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        if (this.amount >= 999)
            this.amount = 999;
        if (this.amount <= -999)
            this.amount = -999;
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.amount);
    }
}
