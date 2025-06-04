package researchersmod.powers.experiments;

import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.actions.killExperiment;
import researchersmod.actions.triggerCompletion;
import researchersmod.actions.triggerExperiment;
import researchersmod.actions.triggerTerminate;
import researchersmod.cards.ExperimentCard;
import researchersmod.powers.BasePower;
import researchersmod.powers.IonSurgePower;
import researchersmod.util.ExperimentPower;
import researchersmod.util.ExperimentUtil;
import researchersmod.util.Wiz;

public class FolderExperiment extends BasePower implements InvisiblePower, NonStackablePower, ExperimentPower {

    public static final String POWER_ID = Researchers.makeID(IonSurgePower.class.getSimpleName());
    public static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURNBASED = false;

    private int B = 4;

    private AbstractCard c;

    public FolderExperiment(AbstractCreature owner, int amount, boolean upgraded, AbstractCard card) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount);
        if(upgraded) {
           B = 6;
        }
        c = card;
        Wiz.atb(new triggerExperiment(this));
        ((ExperimentCard) c).Trial = amount;
    }

    public void terminateEffect(){
        Wiz.atb(new DrawCardAction(1));
        Wiz.atb(new triggerTerminate(this));
        Wiz.atb(new killExperiment(c));
        Wiz.att(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    public void completionEffect(){
        Wiz.atb(new GainBlockAction(owner, B));
        this.amount = this.amount - 1;
        ((ExperimentCard) c).Trial = amount;
        Wiz.atb(new triggerCompletion(this));
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            completionEffect();
            if (this.amount <= 0) {
                terminateEffect();
            }
        }
    }

}
