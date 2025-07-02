package researchersmod.powers;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import researchersmod.Researchers;
import researchersmod.actions.ExhumeToDrawAction;
import researchersmod.util.Wiz;

public class EclipsedFormPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(EclipsedFormPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;

    private int InitialHandSize;

    public EclipsedFormPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURNBASED, owner, 1);
        updateDescription();
        this.amount2 = 1;
        InitialHandSize = BaseMod.MAX_HAND_SIZE;
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        Wiz.atb(new DrawCardAction(this.amount2));
        this.amount2 += this.amount;
        BaseMod.MAX_HAND_SIZE += this.amount;
    }

    public void onVictory() {
        BaseMod.MAX_HAND_SIZE = InitialHandSize;
    }

    public void updateDescription() {
        String plural = "s";
        if(this.amount2 == 1) plural = "";
        this.description = String.format(DESCRIPTIONS[0],this.amount2,plural,this.amount);
    }
}
