package researchersmod.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import researchersmod.Researchers;
import researchersmod.cardmods.PhaseMod;

public class InfluxPowerPower extends BasePower implements PhaseMod.onPhaseInterface {
    public static final String POWER_ID = Researchers.makeID(InfluxPowerPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;

    public InfluxPowerPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount);
        updateDescription();
    }
    @Override
    public void onPhase () {
        AbstractPlayer p = AbstractDungeon.player;
        addToBot(new GainBlockAction(p, p, this.amount));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
