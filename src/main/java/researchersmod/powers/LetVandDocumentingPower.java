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

    public LetVandDocumentingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount);
        updateDescription();
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
            Wiz.atb(new DrawCardAction(amount));
        }
    }

    public void updateDescription() {
        String plural = "s";
        if(this.amount == 1)
            plural = "";
        this.description = String.format(DESCRIPTIONS[0],this.amount,plural,this.amount,plural);
    }
}
