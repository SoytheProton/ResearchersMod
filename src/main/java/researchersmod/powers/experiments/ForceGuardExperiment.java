package researchersmod.powers.experiments;

import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.powers.BasePower;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.util.CalcUtil;
import researchersmod.util.Wiz;

public class ForceGuardExperiment extends BasePower implements InvisiblePower, NonStackablePower, ExperimentPower {

    public static final String POWER_ID = Researchers.makeID(ForceGuardExperiment.class.getSimpleName());
    public static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURNBASED = false;
    private int B;
    public ForceGuardExperiment(AbstractCreature owner, int amount, AbstractCard card, int block) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount,card);
        B = block;
    }

    public void terminateEffect(){
        ExperimentCardManager.remExp(k,this);
    }

    public void completionEffect(){
        Wiz.atb(new GainBlockAction(owner, (int) CalcUtil.CalcBlock(B)));
        ExperimentCardManager.tickExperiment(this);
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.SKILL) {
            ExperimentCardManager.complete(this);
        }
    }

}
