package researchersmod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import researchersmod.Researchers;
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
            Wiz.atb(new ApplyPowerAction(this.owner,this.owner, new GainStrengthPower(this.owner,this.amount)));
        }
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.amount) + String.format(DESCRIPTIONS[1],this.amount);
    }
    // possibly dynamically change icon depending on if it has triggered or not
}
