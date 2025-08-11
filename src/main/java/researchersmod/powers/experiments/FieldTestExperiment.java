package researchersmod.powers.experiments;

import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import researchersmod.Researchers;
import researchersmod.powers.BasePower;
import researchersmod.powers.interfaces.ExperimentInterfaces;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.util.Wiz;

import java.util.Objects;

public class FieldTestExperiment extends BasePower implements InvisiblePower, NonStackablePower, ExperimentPower, ExperimentInterfaces.OnCompletionInterface {

    public static final String POWER_ID = Researchers.makeID(FieldTestExperiment.class.getSimpleName());
    public static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURNBASED = false;

    public FieldTestExperiment(AbstractCreature owner, int amount, AbstractCard card) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount, card);
    }

    public void terminateEffect(){
        Wiz.atb(new SFXAction("ATTACK_IRON_1"));
        Wiz.atb(new DamageAllEnemiesAction((AbstractPlayer) owner, expCard().multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE));
        ExperimentCardManager.remExp(this,true);
    }

    public void completionEffect(){
        Wiz.applyToSelf(new StrengthPower(owner, expCard().magicNumber));
        Wiz.applyToSelf(new LoseStrengthPower(owner, expCard().magicNumber));
        ExperimentCardManager.tickExperiment(this);
    }

    @Override
    public void onCompletion(AbstractPower power) {
        if(!Objects.equals(power.ID, POWER_ID)) {
            ExperimentCardManager.complete(this);
        }
    }
}
