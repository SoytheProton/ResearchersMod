package researchersmod.powers.deprecated;

import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.powers.BasePower;
import researchersmod.powers.interfaces.DescriptionModifier;
import researchersmod.powers.interfaces.ExperimentInterfaces;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.util.ExpDescriptionHelper;
import researchersmod.util.Wiz;

public class OrganizationExpAttachment extends BasePower implements InvisiblePower, NonStackablePower, ExperimentInterfaces.OnCompletionInterface, ExperimentInterfaces.OnTerminateInterface, DescriptionModifier {

    public static final String POWER_ID = Researchers.makeID(OrganizationExpAttachment.class.getSimpleName());
    public static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURNBASED = false;
    private boolean upgraded = false;


    public OrganizationExpAttachment(AbstractCreature owner, AbstractCard card, boolean isUpgraded) {
        super(POWER_ID, TYPE, TURNBASED, owner, 2);
        for(AbstractPower p : owner.powers) {
            if(p instanceof ExperimentPower) {
                if(((BasePower) p).k == card) {
                    attachedPower = p;
                }
            }
        }
        upgraded = isUpgraded;
    }


    @Override
    public void onCompletion(AbstractPower power) {
        if(power == attachedPower) {
            Wiz.atb(new DrawCardAction(this.amount));
        }
    }

    @Override
    public void onTerminate(AbstractPower power) {
        if(power == attachedPower) {
            if(!upgraded)
                ((BasePower) power).k.tags.add(Researchers.EXHAUSTEXP);
            addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,this));
        }
    }

    @Override
    public String ModifyDescription(String rawDescription) {
        String returnString = ExpDescriptionHelper.completionEffect(rawDescription,DESCRIPTIONS[0]);
        if(!upgraded && !returnString.contains(": Exhaust."))
            returnString = ExpDescriptionHelper.terminationEffect(returnString,DESCRIPTIONS[1]);
        return returnString;
    }
}
