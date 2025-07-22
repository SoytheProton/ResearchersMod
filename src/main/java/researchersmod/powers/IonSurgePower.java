package researchersmod.powers;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.actions.CompletionAction;
import researchersmod.powers.interfaces.ExperimentInterfaces;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.util.Wiz;

public class IonSurgePower extends BasePower implements ExperimentInterfaces.OnTerminateInterface {
    public static final String POWER_ID = Researchers.makeID(IonSurgePower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;

    public IonSurgePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount);
        updateDescription();
    }

    @Override
    public void onTerminate(AbstractPower power) {
        for(AbstractPower p : owner.powers) {
            if(p instanceof ExperimentPower) {
                for(int i = this.amount; i>0; i--) Wiz.atb(new CompletionAction((AbstractPlayer) owner,p));
            }
        }
    }
    public void updateDescription() {
        String plural = "s";
        if(this.amount == 1) plural = "";
        this.description = String.format(DESCRIPTIONS[0],this.amount,plural);
    }
}
