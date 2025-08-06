package researchersmod.powers.experiments;

import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import researchersmod.Researchers;
import researchersmod.actions.ManualExperimentAction;
import researchersmod.cards.colorless.DataOverride;
import researchersmod.powers.BasePower;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.util.CalcUtil;
import researchersmod.util.Wiz;

public class DataOverrideExperiment extends BasePower implements InvisiblePower, NonStackablePower, ExperimentPower {

    public static final String POWER_ID = Researchers.makeID(DataOverrideExperiment.class.getSimpleName());
    public static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURNBASED = false;

    private final int block;
    private final int damage;

    public DataOverrideExperiment(AbstractCreature owner, int amount, AbstractCard card, int block, int damage) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount,card);
        this.block = block;
        this.damage = damage;
    }

    public void terminateEffect(){
        AbstractCard tmp = new DataOverride();
        tmp.use((AbstractPlayer) owner, (AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng));
        Wiz.atb(new ManualExperimentAction(tmp));
        ExperimentCardManager.remExp(k,this,true);
    }

    public void completionEffect(){
        Wiz.atb(new GainBlockAction(owner,(int) CalcUtil.CalcBlock(block)));
        Wiz.atb(new SFXAction("BLUNT_FAST"));
        Wiz.atb(new DamageAllEnemiesAction((AbstractPlayer) owner,damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        ExperimentCardManager.tickExperiment(this);
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.POWER) {
            ExperimentCardManager.complete(this);
        }
    }

}
