package researchersmod.powers.deprecated;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.cardmods.PhaseMod;
import researchersmod.powers.BasePower;

public class PhaseNextCardPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(PhaseNextCardPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;

    public PhaseNextCardPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount);
        updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.SKILL || card.type == AbstractCard.CardType.ATTACK) {
            flash();
            AbstractCardModifier Phase = new PhaseMod();
            Phase.onExhausted(card);
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    public void updateDescription() {
        String plural = "s";
        if(this.amount == 1)
            plural = "";
        String plural2 = "are";
        if(this.amount == 1) plural2 = "is";
        this.description = String.format(DESCRIPTIONS[0],this.amount,plural,plural,plural2);
    }
}
