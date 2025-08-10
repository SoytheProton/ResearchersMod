package researchersmod.powers.deprecated;
/*
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.actions.ExhumeToDrawAction;
import researchersmod.powers.BasePower;
import researchersmod.util.Wiz;
@SuppressWarnings("all")
public class OldEclipsedFormPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(OldEclipsedFormPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;

    public OldEclipsedFormPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURNBASED, owner, 1);
        updateDescription();
    }

    @Override
    public void onExhaust(AbstractCard card) {
        Wiz.atb(new ExhumeToDrawAction(false,this.amount));
    }

    public void updateDescription() {
        String plural = "s";
        if(this.amount2 == 1) plural = "";
        this.description = String.format(DESCRIPTIONS[0],this.amount2,plural,this.amount2,plural,this.amount);
    }
}
*/