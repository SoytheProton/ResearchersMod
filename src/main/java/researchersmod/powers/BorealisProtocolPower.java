package researchersmod.powers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.actions.BorealisProtocolAction;
import researchersmod.actions.DelayAction;
import researchersmod.util.Wiz;

public class BorealisProtocolPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(BorealisProtocolPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;
    public BorealisProtocolPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURNBASED, owner, 1);
        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        Wiz.atb(new DelayAction(new BorealisProtocolAction((AbstractPlayer) owner,this.amount)));

    }
    public void updateDescription() {
        String plural = "s";
        if(this.amount == 1) plural = "";
        this.description = String.format(DESCRIPTIONS[0],this.amount,plural);
    }
}
