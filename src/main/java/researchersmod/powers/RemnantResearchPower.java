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

    public RemnantResearchPower(AbstractCreature owner, int amt) {
        super(POWER_ID, TYPE, TURNBASED, owner, amt);
        updateDescription();
    }
    @Override
    public void atEndOfRound() {
        Wiz.atb(new GainBlockAction(Wiz.adp(),Researchers.CardsExhaustedThisTurn * this.amount));
        Wiz.atb(new RemoveSpecificPowerAction(Wiz.adp(), Wiz.adp(),this));
    }

    @Override
    public void onExhaust(AbstractCard card) {
        updateDescription();
    }

    public void updateDescription() {
        if(Researchers.CardsExhaustedThisTurn > 1)
            this.description = String.format(DESCRIPTIONS[0],this.amount) + String.format(DESCRIPTIONS[1],Researchers.CardsExhaustedThisTurn);
        else if(Researchers.CardsExhaustedThisTurn == 1)
            this.description = String.format(DESCRIPTIONS[0],this.amount) + String.format(DESCRIPTIONS[2],Researchers.CardsExhaustedThisTurn);
        else
            this.description = String.format(DESCRIPTIONS[0],this.amount);
    }
}
