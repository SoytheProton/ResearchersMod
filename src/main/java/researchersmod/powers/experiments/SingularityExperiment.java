package researchersmod.powers.experiments;

import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import researchersmod.Researchers;
import researchersmod.cards.ExperimentCard;
import researchersmod.powers.BasePower;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.ui.ExperimentPowerFields;
import researchersmod.util.CalcUtil;
import researchersmod.util.Wiz;

public class SingularityExperiment extends BasePower implements InvisiblePower, NonStackablePower, ExperimentPower {

    public static final String POWER_ID = Researchers.makeID(SingularityExperiment.class.getSimpleName());
    public static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURNBASED = false;

    private final int block;

    public SingularityExperiment(AbstractCreature owner, int amount, AbstractCard card, int block) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount,card);
        this.block = block;
    }

    public void terminateEffect(){
        ExperimentCard kard = ((ExperimentCard) ExperimentPowerFields.attachedCard.get(this));
        if(kard.baseTrial > 1) {
            AbstractCard tmp = kard.makeTrialCopy(kard.baseTrial - 1);
            ((ExperimentCard) tmp).baseTrial = kard.baseTrial - 1;
            ((ExperimentCard) tmp).trial = kard.baseTrial - 1;
            if(tmp.baseBlock > 0) tmp.baseBlock = kard.baseBlock - 1;
            ExperimentCardManager.addExperiment(tmp);
            tmp.use((AbstractPlayer) owner, (AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng));
        }
        ExperimentCardManager.remExp(this,true);
    }

    public void completionEffect(){
        Wiz.atb(new GainBlockAction(owner,(int) CalcUtil.CalcBlock(block)));
        ExperimentCardManager.tickExperiment(this);
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        ExperimentCardManager.complete(this);
    }

}
