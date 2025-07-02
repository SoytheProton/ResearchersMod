package researchersmod.powers;

import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.cards.ExperimentCard;
import researchersmod.util.Wiz;

public class FreeExperimentsPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(FreeExperimentsPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = true;

    public FreeExperimentsPower(AbstractCreature owner, int amt) {
        super(POWER_ID, TYPE, TURNBASED, owner, amt);
        updateDescription();
    }

    @Override
    public void atEndOfRound() {
        if(this.amount == 0)
            Wiz.atb(new RemoveSpecificPowerAction(this.owner,this.owner,POWER_ID));
        else
            Wiz.atb(new ReducePowerAction(this.owner,this.owner,POWER_ID,1));
    }

    public void onUseCard(AbstractCard card) {
        if(card instanceof ExperimentCard)
            flash();
    }

    public void updateDescription() {
        String plural = "s";
        if(this.amount == 1)
            plural = "";
        this.description = String.format(DESCRIPTIONS[0],this.amount,plural);
    }
}
