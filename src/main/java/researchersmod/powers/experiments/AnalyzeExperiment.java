package researchersmod.powers.experiments;

import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.actions.IncreaseMiscAttackAction;
import researchersmod.powers.BasePower;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.util.CalcUtil;
import researchersmod.util.ExpUtil;
import researchersmod.util.ExperimentPower;
import researchersmod.util.Wiz;

import java.util.Objects;

public class AnalyzeExperiment extends BasePower implements InvisiblePower, NonStackablePower, ExperimentPower, ExpUtil.onCompletionInterface
{

    public static final String POWER_ID = Researchers.makeID(AnalyzeExperiment.class.getSimpleName());
    public static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURNBASED = false;


    private int M;
    private int ExperimentTracker = 3;

    public AnalyzeExperiment(AbstractCreature owner, int amount, AbstractCard card, int magic) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount, card);
        M = magic;
    }

    public void terminateEffect(){
        ExperimentCardManager.remExp(k,true);
        Wiz.att(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    public void completionEffect(){
        Wiz.atb(new IncreaseMiscAttackAction(k.uuid,k.misc,M));
        ExperimentCardManager.tickExperiment(this);
    }

    @Override
    public void onCompletion(AbstractPower power) {
        ExperimentTracker--;
        if(ExperimentTracker == 0) {
            ExperimentTracker = 3;
            completionEffect();
            if (this.amount <= 0) {
                terminateEffect();
            }
        }
    }

}
