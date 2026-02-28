package researchersmod.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.actions.common.ApplyDistortionPowerToAll;
import researchersmod.actions.common.ManualExperimentAction;
import researchersmod.cards.status.ShortCircuit;
import researchersmod.powers.experiments.ShortCircuitExperiment;
import researchersmod.powers.interfaces.ExperimentInterfaces;
import researchersmod.util.Wiz;

import java.util.Objects;

public class SystemErrorPower extends BasePower implements ExperimentInterfaces.OnTerminateInterface {
    public static final String POWER_ID = Researchers.makeID(SystemErrorPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;
    public SystemErrorPower(AbstractCreature owner, int amt) {
        super(POWER_ID, TYPE, TURNBASED, owner, amt);
        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        for(int i = amount; i>0; i--) {
            AbstractCard card = new ShortCircuit();
            card.dontTriggerOnUseCard = true;
            card.use((AbstractPlayer) owner,(AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng));
            Wiz.atb(new ManualExperimentAction(card));
        }
    }

    public void updateDescription() {
        String plural = "s";
        if(this.amount == 1) plural = "";
        this.description = String.format(DESCRIPTIONS[0],this.amount,plural,this.amount);
    }

    @Override
    public void onTerminate(AbstractPower power) {
        if(Objects.equals(power.ID, ShortCircuitExperiment.POWER_ID)) {
            flashWithoutSound();
            Wiz.atb(new ApplyDistortionPowerToAll(amount));
        }
    }
}
