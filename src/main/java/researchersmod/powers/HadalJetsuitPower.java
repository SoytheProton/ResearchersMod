package researchersmod.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.actions.unique.HadalJetsuitAction;

public class HadalJetsuitPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(HadalJetsuitPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;

    public HadalJetsuitPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount);
        updateDescription();
    }
    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.amount);
    }

    public void onExhaust(AbstractCard card) {
        flash();
        addToBot(new HadalJetsuitAction(this.amount));
    }
}
