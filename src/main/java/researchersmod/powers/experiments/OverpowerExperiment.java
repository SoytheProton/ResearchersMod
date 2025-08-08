package researchersmod.powers.experiments;

import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.powers.BasePower;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.util.Wiz;

public class OverpowerExperiment extends BasePower implements InvisiblePower, NonStackablePower, ExperimentPower {

    public static final String POWER_ID = Researchers.makeID(OverpowerExperiment.class.getSimpleName());
    public static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURNBASED = false;

    public OverpowerExperiment(AbstractCreature owner, int amount, AbstractCard card) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount,card);
    }

    public void terminateEffect(){
        Wiz.atb(new DrawCardAction(expCard().magicNumber));
        ExperimentCardManager.remExp(this);
    }

    public void completionEffect(){
        ExperimentCardManager.tickExperiment(this);
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.POWER) {
            ExperimentCardManager.complete(this);
        }
    }

}
