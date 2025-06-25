package researchersmod.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.util.Wiz;

public class RemnantResearchPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(RemnantResearchPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = true;
    private boolean firstCardExhaustedThisTurn = false;

    public RemnantResearchPower(AbstractCreature owner, int amt) {
        super(POWER_ID, TYPE, TURNBASED, owner, amt);
        updateDescription();
        firstCardExhaustedThisTurn = true;
    }
    @Override
    public void atEndOfRound() {
        Wiz.atb(new GainBlockAction(Wiz.adp(),Researchers.CardsExhaustedThisTurn * this.amount));
        Wiz.atb(new RemoveSpecificPowerAction(Wiz.adp(), Wiz.adp(),this));
        firstCardExhaustedThisTurn = false;
    }

    @Override
    public void onExhaust(AbstractCard card) {
        updateDescription();
        firstCardExhaustedThisTurn = true;
    }

    public void updateDescription() {
        int i = Researchers.CardsExhaustedThisTurn;
        if(firstCardExhaustedThisTurn)
            i++;
        if(i > 1)
            this.description = String.format(DESCRIPTIONS[0],this.amount) + String.format(DESCRIPTIONS[1],i);
        else if(i == 1)
            this.description = String.format(DESCRIPTIONS[0],this.amount) + String.format(DESCRIPTIONS[2],i);
        else
            this.description = String.format(DESCRIPTIONS[0],this.amount);
    }
}
