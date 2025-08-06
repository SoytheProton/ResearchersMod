package researchersmod.powers;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.util.Wiz;

public class EclipsedFormPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(EclipsedFormPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;

    private int InitialHandSize;

    public EclipsedFormPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURNBASED, owner, 1);
        updateDescription();
        InitialHandSize = BaseMod.MAX_HAND_SIZE;
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        Wiz.atb(new DrawCardAction(this.amount * 3));
        BaseMod.MAX_HAND_SIZE += this.amount;
        updateDescription();
    }

    public void onVictory() {
        BaseMod.MAX_HAND_SIZE = InitialHandSize;
    }

    public void updateDescription() {
        String plural = "s";
        if(this.amount2 == 1) plural = "";
        this.description = String.format(DESCRIPTIONS[0],this.amount * 3,plural,this.amount, BaseMod.MAX_HAND_SIZE);
    }
}
