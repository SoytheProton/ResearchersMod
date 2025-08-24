package researchersmod.powers.experiments;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.actions.common.InstantAttackDamageRandomEnemyAction;
import researchersmod.cardmods.ExperimentMod;
import researchersmod.cards.ExperimentCard;
import researchersmod.fields.ExperimentPowerFields;
import researchersmod.powers.BasePower;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.util.Wiz;

public class MultithreadExperiment extends BasePower implements InvisiblePower, NonStackablePower, ExperimentPower {

    public static final String POWER_ID = Researchers.makeID(MultithreadExperiment.class.getSimpleName());
    public static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURNBASED = false;
    public MultithreadExperiment(AbstractCreature owner, int amount, AbstractCard card) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount,card);
    }

    public void terminateEffect(){
        ExperimentCard kard = (ExperimentCard) expCard();
        AbstractCard card = kard.makeTrialCopy(kard.baseTrial + 1);
        CardModifierManager.removeModifiersById(card, ExperimentMod.ID,true);
        ((ExperimentCard) card).baseTrial = kard.baseTrial + 1;
        ((ExperimentCard) card).trial = kard.baseTrial + 1;
        card.applyPowers();
        Wiz.atb(new MakeTempCardInDiscardAction(card,2));
        ExperimentCardManager.remExp(this,true);
    }

    public void completionEffect(){
        Wiz.atb(new SFXAction("ATTACK_DAGGER_1"));
        Wiz.atb(new InstantAttackDamageRandomEnemyAction(ExperimentPowerFields.attachedCard.get(this), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        ExperimentCardManager.tickExperiment(this);
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.SKILL) {
            ExperimentCardManager.complete(this);
        }
    }

}
