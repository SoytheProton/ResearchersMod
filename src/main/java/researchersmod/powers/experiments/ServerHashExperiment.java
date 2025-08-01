package researchersmod.powers.experiments;

import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.powers.BasePower;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.util.CalcUtil;
import researchersmod.util.Wiz;

public class ServerHashExperiment extends BasePower implements InvisiblePower, NonStackablePower, ExperimentPower {

    public static final String POWER_ID = Researchers.makeID(ServerHashExperiment.class.getSimpleName());
    public static final AbstractPower.PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURNBASED = false;


    private int B;

    public ServerHashExperiment (AbstractCreature owner, int amount, AbstractCard card, int block) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount,card);
        B = block;
    }

    public void terminateEffect(){
        Wiz.atb(new DrawCardAction(1));
        ExperimentCardManager.remExp(k,this);
    }

    public void completionEffect(){
        Wiz.atb(new GainBlockAction(owner, (int)CalcUtil.CalcBlock(B)));
        ExperimentCardManager.tickExperiment(this);
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK)
            ExperimentCardManager.complete(this);
    }

}
