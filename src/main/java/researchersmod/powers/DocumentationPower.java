package researchersmod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;

public class DocumentationPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(DocumentationPower.class.getSimpleName());
    public static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURNBASED = false;

    public DocumentationPower(AbstractCreature owner, int blockAmt) {
        super(POWER_ID, TYPE, TURNBASED, owner, 1);
        amount = blockAmt;
        updateDescription();
    }


    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.POWER) {
            flash();
            addToTop((AbstractGameAction)new GainBlockAction(this.owner, this.amount));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
