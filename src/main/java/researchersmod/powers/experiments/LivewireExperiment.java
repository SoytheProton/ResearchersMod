package researchersmod.powers.experiments;

import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.cards.status.ShortCircuit;
import researchersmod.powers.BasePower;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.util.Wiz;

public class LivewireExperiment extends BasePower implements InvisiblePower, NonStackablePower, ExperimentPower {

    public static final String POWER_ID = Researchers.makeID(LivewireExperiment.class.getSimpleName());
    public static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURNBASED = false;

    public LivewireExperiment(AbstractCreature owner, int amount, AbstractCard card) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount,card);
    }

    public void terminateEffect(){
        ExperimentCardManager.remExp(this);
    }

    public void completionEffect(){
        Wiz.atb(new GainEnergyAction(expCard().magicNumber));
        Wiz.atb(new MakeTempCardInDiscardAction(new ShortCircuit(),1));
        ExperimentCardManager.tickExperiment(this);
    }
    public void onUseCard(AbstractCard card, UseCardAction action) {
        ExperimentCardManager.complete(this);
    }

}
