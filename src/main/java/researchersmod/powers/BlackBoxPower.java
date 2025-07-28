package researchersmod.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import researchersmod.Researchers;
import researchersmod.cardmods.PhaseMod;
import researchersmod.util.Wiz;

import java.util.Objects;

public class BlackBoxPower extends BasePower implements PhaseMod.OnPhaseInterface {
    public static final String POWER_ID = Researchers.makeID(BlackBoxPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;

    public BlackBoxPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount);
        updateDescription();
    }

    @Override
    public void onPhase(AbstractCard card) {
        flash();
        Wiz.applyToSelf(new MetallicizePower(owner,this.amount));
    }

    @Override
    public void atStartOfTurn() {
        flash();
        int i = 0;
        for(AbstractPower p : owner.powers) {
            if(Objects.equals(p.ID, MetallicizePower.POWER_ID)) {
              i = p.amount / 2;
            }
        }
        Wiz.atb(new ReducePowerAction(owner, owner, MetallicizePower.POWER_ID,i));
    }
    public void updateDescription() {
        String plural = "s";
        if(this.amount == 1) plural = "";
        this.description = String.format(DESCRIPTIONS[0],this.amount,plural);
    }
}
