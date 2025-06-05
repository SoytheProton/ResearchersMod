package researchersmod.powers.experiments;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.actions.killExperiment;
import researchersmod.actions.triggerCompletion;
import researchersmod.actions.triggerExperiment;
import researchersmod.actions.triggerTerminate;
import researchersmod.cardmods.ExperimentMod;
import researchersmod.cards.ExperimentCard;
import researchersmod.powers.BasePower;
import researchersmod.util.ExperimentPower;
import researchersmod.util.ExpUtil;
import researchersmod.util.Wiz;

import java.util.Objects;

public class FolderExperiment extends BasePower implements InvisiblePower, NonStackablePower, ExperimentPower, ExpUtil.onCompletionInterface {

    public static final String POWER_ID = Researchers.makeID(FolderExperiment.class.getSimpleName());
    public static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURNBASED = false;


    public FolderExperiment(AbstractCreature owner, int amount, AbstractCard card) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount);
        k = card;
        Wiz.atb(new triggerExperiment(this));
        ExpUtil.tickExperiment(this);
        PriorityActivation = true;
    }

    public void terminateEffect(){
        Wiz.atb(new triggerTerminate(this));
        Wiz.atb(new killExperiment(k,true));
        Wiz.att(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    public void completionEffect(){
        ExpUtil.tickExperiment(1,this);
        Wiz.atb(new triggerCompletion(this));
    }

    @Override
    public void onCompletion(AbstractPower power) {
        if(!Objects.equals(power.ID, POWER_ID)) {
            ExpUtil.tickExperiment(-1,power);
            completionEffect();
            if (this.amount <= 0) {
                terminateEffect();
            }
        }
    }
}
