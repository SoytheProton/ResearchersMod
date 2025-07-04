package researchersmod.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.cardmods.PhaseMod;
import researchersmod.util.Wiz;

public class SystemErrorPower extends BasePower implements PhaseMod.OnPhaseInterface {
    public static final String POWER_ID = Researchers.makeID(SystemErrorPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = true;
    public SystemErrorPower(AbstractCreature owner, int amt) {
        super(POWER_ID, TYPE, TURNBASED, owner, amt);
        updateDescription();
    }


    public void updateDescription() {
        String plural = "s";
        if(this.amount == 1) plural = "";
        this.description = String.format(DESCRIPTIONS[0],this.amount,plural);
    }

    @Override
    public void onPhase(AbstractCard card) {
        Wiz.atb(new DrawCardAction(2));
        Wiz.atb(new ReducePowerAction(owner, owner, this, 1));
    }
}
