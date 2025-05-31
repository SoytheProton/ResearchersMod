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
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.actions.*;
import researchersmod.powers.BasePower;
import researchersmod.powers.IonSurgePower;
import researchersmod.util.ExperimentPower;
import researchersmod.util.ExperimentUtil;
import researchersmod.util.Wiz;

public class ServerHashExperiment extends BasePower implements InvisiblePower, NonStackablePower, ExperimentPower {

    public static final String POWER_ID = Researchers.makeID(IonSurgePower.class.getSimpleName());
    public static final AbstractPower.PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURNBASED = false;


    private int B;
    private AbstractCard c;

    public ServerHashExperiment (AbstractCreature owner, int amount, AbstractCard card, int block) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount);
        B = block;
        c = card;
        Wiz.atb(new triggerExperiment(this));
        ExperimentUtil.ExperimentDescription(c,amount);
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
        ExperimentUtil.ExperimentDescription(c,amount);
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
