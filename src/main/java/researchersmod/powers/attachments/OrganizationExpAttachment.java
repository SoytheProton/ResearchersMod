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
import researchersmod.fields.ExperimentPowerFields;
import researchersmod.powers.BasePower;
import researchersmod.powers.interfaces.DescriptionModifier;
import researchersmod.powers.interfaces.ExperimentInterfaces;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.util.ExpDescriptionHelper;
import researchersmod.util.Wiz;

public class OrganizationExpAttachment extends BasePower implements InvisiblePower, NonStackablePower, ExperimentInterfaces.OnAnyCompletionInterface, ExperimentInterfaces.OnTerminateInterface, DescriptionModifier {

    public static final String POWER_ID = Researchers.makeID(OrganizationExpAttachment.class.getSimpleName());
    public static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURNBASED = false;


    public OrganizationExpAttachment(AbstractCreature owner, AbstractCard card, int amount) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount);
        for(AbstractPower p : owner.powers) {
            if(p instanceof ExperimentPower) {
                if(ExperimentPowerFields.attachedCard.get(p) == card) {
                    ExperimentPowerFields.attachedPower.set(this,p);
                }
            }
        }
    }

    public boolean isStackable(AbstractPower po) {
        if(po instanceof OrganizationExpAttachment) {
            return ExperimentPowerFields.attachedPower.get(po) == ExperimentPowerFields.attachedPower.get(this);
        }
        return false;
    }



    @Override
    public void onCompletion(AbstractPower power) {
        if(power == ExperimentPowerFields.attachedPower.get(this)) {
            Wiz.atb(new DrawCardAction(this.amount));
        }
    }

    @Override
    public void onTerminate(AbstractPower power) {
        if(power == ExperimentPowerFields.attachedPower.get(this)) {
            addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,this));
        }
    }

    @Override
    public String ModifyDescription(String rawDescription) {
        return ExpDescriptionHelper.completionEffect(rawDescription,String.format(DESCRIPTIONS[0],this.amount));
    }
}
