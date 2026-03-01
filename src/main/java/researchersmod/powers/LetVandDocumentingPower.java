package researchersmod.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.cards.status.BurntDocument;
import researchersmod.util.Wiz;

public class LetVandDocumentingPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(LetVandDocumentingPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = true;
    public LetVandDocumentingPower(AbstractCreature owner, int amount, int amount2) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount);
        this.amount2 = amount2;
        updateDescription();
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        amount2++;
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        for(int i = amount; i>0; i--) {
            AbstractCard card = new BurntDocument();
            Wiz.atb(new MakeTempCardInHandAction(card));
        }
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(card.type == AbstractCard.CardType.STATUS) {
            flash();
            Wiz.atb(new DrawCardAction(amount2));
        }
    }

    public void updateDescription() {
        String plural = "s";
        if(this.amount == 1)
            plural = "";
        String plural2 = "s";
        if(this.amount2 == 1)
            plural2 = "";
        this.description = String.format(DESCRIPTIONS[0],this.amount2,plural2,this.amount,plural);
    }
}
