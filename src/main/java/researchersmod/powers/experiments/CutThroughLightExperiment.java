package researchersmod.powers.experiments;

import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.powers.BasePower;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.util.Wiz;

public class CutThroughLightExperiment extends BasePower implements InvisiblePower, NonStackablePower, ExperimentPower {

    public static final String POWER_ID = Researchers.makeID(CutThroughLightExperiment.class.getSimpleName());
    public static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURNBASED = false;
    private final int magicNumber;
    public CutThroughLightExperiment(AbstractCreature owner, int amount, AbstractCard card, int magicNumber) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount,card);
        this.magicNumber = magicNumber;
    }

    public void terminateEffect(){
        ExperimentCardManager.remExp(k,this);
    }

    public void completionEffect(){
        Wiz.atb(new BetterDiscardPileToHandAction(magicNumber));
        ExperimentCardManager.tickExperiment(this);
    }

    public void atStartOfTurnPostDraw() {
        ExperimentCardManager.complete(this);
    }

}
