package researchersmod.powers.deprecated;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.actions.addPhaseAndEthereal;
import researchersmod.powers.BasePower;

public class IonSurgePower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(IonSurgePower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;

    public IonSurgePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount);
        updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.POWER) {
            flash();
            addToBot(new addPhaseAndEthereal(this.amount,true));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
