package researchersmod.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.util.Wiz;

public class WingbeatPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(WingbeatPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;
    private int ExhaustedPreviousTurn = 0;
    private boolean firstCardExhaustedThisTurn = false;

    public WingbeatPower(AbstractCreature owner,int amt) {
        super(POWER_ID, TYPE, TURNBASED, owner, amt);
        updateDescription();
    }
    @Override
    public void atEndOfRound() {
        ExhaustedPreviousTurn = Researchers.CardsExhaustedThisTurn * this.amount;
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        firstCardExhaustedThisTurn = false;
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new DrawCardAction(this.owner,ExhaustedPreviousTurn));
        ExhaustedPreviousTurn = 0;
    }

    @Override
    public void onExhaust(AbstractCard card) {
        firstCardExhaustedThisTurn = true;
        updateDescription();
    }

    public void updateDescription() {
        int i = Researchers.CardsExhaustedThisTurn;
        if(firstCardExhaustedThisTurn)
            i++;
        if(amount == 1) {
            if(i == 0)
                this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
            else if (i == 1)
                this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + DESCRIPTIONS[3] + i + DESCRIPTIONS[4];
            else
                this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + DESCRIPTIONS[3] + i + DESCRIPTIONS[5];
        } else {
            if(i == 0)
                this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
            else if (i == 1)
                this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2] + DESCRIPTIONS[3] + i + DESCRIPTIONS[4];
            else
                this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2] + DESCRIPTIONS[3] + i + DESCRIPTIONS[5];
        }
    }
}
