package researchersmod.powers;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.cards.status.PlasmicEnergy;

public class TeslaCoilPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(TeslaCoilPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;

    public TeslaCoilPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount);
        updateDescription();
    }

    public void atStartOfTurnPostDraw() {
        flash();
        addToBot(new MakeTempCardInHandAction(new PlasmicEnergy(), this.amount));
    }

    public void updateDescription() {
        String plural = "ies";
        if(this.amount == 1) plural = "y";
        this.description = String.format(DESCRIPTIONS[0],this.amount,plural);
    }
}
