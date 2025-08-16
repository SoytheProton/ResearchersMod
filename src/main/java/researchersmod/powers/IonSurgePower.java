package researchersmod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import researchersmod.Researchers;
import researchersmod.powers.interfaces.ExperimentInterfaces;
import researchersmod.util.Wiz;

public class IonSurgePower extends BasePower implements ExperimentInterfaces.OnCompletionPowerInterface {
    public static final String POWER_ID = Researchers.makeID(IonSurgePower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;

    public IonSurgePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount);
        this.amount2 = 3;
        updateDescription();
    }

    @Override
    public void onCompletion(AbstractPower power) {
        this.amount2--;
        if(this.amount2 == 0) {
            flash();
            this.amount2 = 3;
            int i = AbstractDungeon.cardRandomRng.random(1, 3);
            switch (i) {
                case 1:
                    Wiz.applyToSelf(new StrengthPower(owner, this.amount));
                    break;
                case 2:
                    Wiz.applyToSelf(new DexterityPower(owner, this.amount));
                    break;
                case 3:
                    Wiz.applyToSelf(new ArtifactPower(owner, this.amount));
                    break;
            }
        }
    }
    public void updateDescription() {
        String plural = "s";
        if(this.amount2 == 1) plural = "";
        this.description = String.format(DESCRIPTIONS[0],this.amount,this.amount2,plural);
    }
}
