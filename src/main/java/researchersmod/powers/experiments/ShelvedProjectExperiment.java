package researchersmod.powers.experiments;

import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.powers.BasePower;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.util.Wiz;

public class ShelvedProjectExperiment extends BasePower implements InvisiblePower, NonStackablePower, ExperimentPower {

    public static final String POWER_ID = Researchers.makeID(ShelvedProjectExperiment.class.getSimpleName());
    public static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURNBASED = false;
    private int damageNumber;
    public ShelvedProjectExperiment(AbstractCreature owner, int amount, AbstractCard card, int damage) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount,card);
        damageNumber = damage;
    }

    public void terminateEffect(){
        ExperimentCardManager.remExp(k,this);
    }

    public void completionEffect(){
        Wiz.atb(new SFXAction("ATTACK_HEAVY"));
        Wiz.atb(new DamageAllEnemiesAction((AbstractPlayer) this.owner,damageNumber, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE));
        ExperimentCardManager.tickExperiment(this);
    }

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        completionEffect();
    }

}
