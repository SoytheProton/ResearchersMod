package researchersmod.powers.experiments;

import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.powers.BasePower;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.util.Wiz;

public class AnalyzeExperiment extends BasePower implements InvisiblePower, NonStackablePower, ExperimentPower
{
    public static final String POWER_ID = Researchers.makeID(AnalyzeExperiment.class.getSimpleName());
    public static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURNBASED = false;

    public AnalyzeExperiment(AbstractCreature owner, int amount, AbstractCard card) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount, card);
    }

    public void terminateEffect(){
        ExperimentCardManager.remExp(this,true);
    }

    public void completionEffect(){
        Wiz.atb(new GainBlockAction(owner,expCard().block));
        ExperimentCardManager.tickExperiment(this);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        ExperimentCardManager.complete(this);
    }

}
