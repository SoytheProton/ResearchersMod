package researchersmod.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import researchersmod.Researchers;
import researchersmod.util.Wiz;

public class BlackBoxPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(BlackBoxPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;

    public BlackBoxPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount);
        updateDescription();
    }

    @Override
    public void onExhaust(AbstractCard card) {
        flash();
        Wiz.applyToSelf(new MetallicizePower(owner,this.amount));
        Wiz.applyToSelf(new LoseMetallicizePower(owner,this.amount));
    }

    public void updateDescription() {
        String plural = "s";
        if(this.amount == 1) plural = "";
        this.description = String.format(DESCRIPTIONS[0],this.amount,plural);
    }
}
