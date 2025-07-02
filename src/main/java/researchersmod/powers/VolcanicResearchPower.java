package researchersmod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import researchersmod.Researchers;
import researchersmod.util.Wiz;

public class VolcanicResearchPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(VolcanicResearchPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;

    public VolcanicResearchPower(AbstractCreature owner, int amt) {
        super(POWER_ID, TYPE, TURNBASED, owner, amt);
        this.amount2 = 1;
        updateDescription();
    }

    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        this.amount2 += 1;
        if (this.amount == 0) {
            addToTop((AbstractGameAction)new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if(card.type == AbstractCard.CardType.STATUS) {
            flash();
            Wiz.atb(new ApplyPowerAction(owner,owner, new StrengthPower(owner,this.amount)));
            Wiz.atb(new DrawCardAction(this.amount2));
        }
    }

    public void updateDescription() {
        String plural = "s";
        if(this.amount2 == 1)
            plural = "";
        this.description = String.format(plural,this.amount2,plural,this.amount);
    }
}
