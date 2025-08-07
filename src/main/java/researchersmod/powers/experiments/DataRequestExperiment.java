package researchersmod.powers.experiments;

import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.powers.BasePower;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.util.CalcUtil;
import researchersmod.util.Wiz;

public class DataRequestExperiment extends BasePower implements InvisiblePower, NonStackablePower, ExperimentPower {

    public static final String POWER_ID = Researchers.makeID(DataRequestExperiment.class.getSimpleName());
    public static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURNBASED = false;

    private final int block;
    private final int damage;

    public DataRequestExperiment(AbstractCreature owner, int amount, AbstractCard card, int block, int damage) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount,card);
        this.block = block;
        this.damage = damage;
    }

    public void terminateEffect(){
        ExperimentCardManager.remExp(this,true);
    }

    public void completionEffect(){
        Wiz.atb(new GainBlockAction(owner,(int) CalcUtil.CalcBlock(block)));
        Wiz.atb(new SFXAction("BLUNT_FAST"));
        Wiz.atb(new DamageRandomEnemyAction(new DamageInfo(owner, (int) CalcUtil.CalcDamage(damage), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        ExperimentCardManager.tickExperiment(this);
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.POWER) {
            ExperimentCardManager.complete(this);
        }
    }

}
