package researchersmod.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import researchersmod.Researchers;

public class LoseMetallicizePower extends BasePower implements NonStackablePower {
    public static final String POWER_ID = Researchers.makeID(LoseMetallicizePower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURNBASED = false;
    public LoseMetallicizePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount);
        this.amount2 = 2;
        updateDescription();
        this.loadRegion("flex");
        this.img = null;
    }

    public void atEndOfTurn(boolean isPlayer) {
        if(amount2 > 1) {
            amount2--;
            updateDescription();
        }
        else {
            this.flash();
            this.addToBot(new ReducePowerAction(this.owner, this.owner, MetallicizePower.POWER_ID, this.amount));
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public boolean isStackable(AbstractPower power) {
        if(power instanceof LoseMetallicizePower) {
            return ((LoseMetallicizePower) power).amount2 == this.amount2;
        }
        return false;
    }

    public void updateDescription() {
        String plural = "s";
        if(this.amount == 1) plural = "";
        this.description = String.format(DESCRIPTIONS[0],this.amount2,plural,amount);
    }
}
