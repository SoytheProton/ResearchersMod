package researchersmod.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.actions.HadalJetsuitAction;
import researchersmod.cardmods.PhaseMod;
import researchersmod.util.Wiz;

public class HadalJetsuitPower extends BasePower implements PhaseMod.OnPhaseInterface {
    public static final String POWER_ID = Researchers.makeID(HadalJetsuitPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;

    public HadalJetsuitPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount);
        this.amount2 = 2;
        updateDescription();
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        this.amount2 += 2;
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.amount2,this.amount);
    }

    @Override
    public void onPhase(AbstractCard card) {
        Wiz.applyToSelf(new ManipulationPower(owner,this.amount2));
        Wiz.applyToSelf(new LoseManipulationPower(owner, this.amount2));
        Wiz.att(new HadalJetsuitAction(this.amount));
    }
}
