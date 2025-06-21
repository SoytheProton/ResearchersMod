package researchersmod.powers;

import basemod.devcommands.draw.Draw;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.util.ExperimentPower;
import researchersmod.util.Wiz;

public class WingbeatPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(WingbeatPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;
    private int ExhaustedPreviousTurn = 0;

    public WingbeatPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURNBASED, owner, 1);
        updateDescription();
    }
    @Override
    public void atEndOfRound() {
        ExhaustedPreviousTurn = Researchers.CardsExhaustedThisTurn * this.amount;
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new DrawCardAction(Wiz.adp(),ExhaustedPreviousTurn));
        ExhaustedPreviousTurn = 0;
    }

    @Override
    public void onExhaust(AbstractCard card) {
        updateDescription();
    }

    public void updateDescription() {
        if(amount == 1) {
            if(Researchers.CardsExhaustedThisTurn == 0)
                this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
            else if (Researchers.CardsExhaustedThisTurn == 1)
                this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + DESCRIPTIONS[3] + Researchers.CardsExhaustedThisTurn + DESCRIPTIONS[4];
            else
                this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + DESCRIPTIONS[3] + Researchers.CardsExhaustedThisTurn + DESCRIPTIONS[5];
        } else {
            if(Researchers.CardsExhaustedThisTurn == 0)
                this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
            else if (Researchers.CardsExhaustedThisTurn == 1)
                this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2] + DESCRIPTIONS[3] + Researchers.CardsExhaustedThisTurn + DESCRIPTIONS[4];
            else
                this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2] + DESCRIPTIONS[3] + Researchers.CardsExhaustedThisTurn + DESCRIPTIONS[5];
        }
    }
}
