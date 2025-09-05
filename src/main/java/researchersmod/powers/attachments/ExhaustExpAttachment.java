package researchersmod.powers.attachments;

import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.fields.ExperimentFields;
import researchersmod.fields.ExperimentPowerFields;
import researchersmod.powers.BasePower;
import researchersmod.powers.EntropyPower;
import researchersmod.powers.interfaces.DescriptionModifier;
import researchersmod.powers.interfaces.ExperimentInterfaces;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.util.ExpDescriptionHelper;
import researchersmod.util.Wiz;

public class ExhaustExpAttachment extends BasePower implements InvisiblePower, NonStackablePower, ExperimentInterfaces.OnTerminateInterface, DescriptionModifier {

    public static final String POWER_ID = Researchers.makeID(ExhaustExpAttachment.class.getSimpleName());
    public static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURNBASED = false;


    public ExhaustExpAttachment(AbstractCreature owner, AbstractCard card) {
        super(POWER_ID, TYPE, TURNBASED, owner, 1);
        for(AbstractPower p : owner.powers) {
            if(p instanceof ExperimentPower) {
                if(ExperimentPowerFields.attachedCard.get(p) == card) {
                    ExperimentPowerFields.attachedPower.set(this,p);
                }
            }
        }
    }

    public boolean isStackable(AbstractPower po) {
        if(po instanceof ExhaustExpAttachment) {
            return ExperimentPowerFields.attachedPower.get(po) == ExperimentPowerFields.attachedPower.get(this);
        }
        return false;
    }

    @Override
    public void onTerminate(AbstractPower power) {
        if(power == ExperimentPowerFields.attachedPower.get(this)) {
            ExperimentFields.exhaustingExperiment.set(ExperimentPowerFields.attachedCard.get(power),true);
            addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,this));
        }
    }

    @Override
    public String ModifyDescription(String rawDescription) {
        String returnString = rawDescription;
        if(!returnString.contains(DESCRIPTIONS[1]))
            returnString = ExpDescriptionHelper.terminationEffect(returnString,DESCRIPTIONS[0]);
        return returnString;
    }
}
