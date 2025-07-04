package researchersmod.powers.experiments;

import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.actions.IncreaseMiscAttackAction;
import researchersmod.powers.BasePower;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.powers.interfaces.ExperimentInterfaces;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.util.Wiz;

public class AnalyzeExperiment extends BasePower implements InvisiblePower, NonStackablePower, ExperimentPower, ExperimentInterfaces.OnCompletionInterface
{

    public static final String POWER_ID = Researchers.makeID(AnalyzeExperiment.class.getSimpleName());
    public static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURNBASED = false;


    private int M;
    private int ExperimentTracker = 2;

    public AnalyzeExperiment(AbstractCreature owner, int amount, AbstractCard card, int magic) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount, card);
        M = magic;
    }

    public void terminateEffect(){
        ExperimentCardManager.remExp(k,this,true);
    }

    public void completionEffect(){
        Wiz.atb(new IncreaseMiscAttackAction(k.uuid,k.misc,M));
        ExperimentCardManager.tickExperiment(this);
    }

    @Override
    public void onCompletion(AbstractPower power) {
        ExperimentTracker--;
        if(ExperimentTracker == 0) {
            ExperimentTracker = 2;
            completionEffect();
        }
    }

}
