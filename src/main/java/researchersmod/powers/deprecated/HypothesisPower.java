package researchersmod.powers.deprecated;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.StrengthPower;
import researchersmod.Researchers;
import researchersmod.powers.BasePower;
import researchersmod.util.Wiz;

public class HypothesisPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(HypothesisPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;
    private boolean triggeredThisTurn = false;

    public HypothesisPower(AbstractCreature owner, int amt) {
        super(POWER_ID, TYPE, TURNBASED, owner, amt);
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        triggeredThisTurn = false;
    }

    @Override
    public void onExhaust(AbstractCard card) {
        if(!triggeredThisTurn) {
            flash();
            Wiz.atb(new ApplyPowerAction(this.owner,this.owner, new StrengthPower(this.owner,this.amount),this.amount));
            triggeredThisTurn = true;
        }
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.amount);
    }
    // possibly dynamically change icon depending on if it has triggered or not
}
