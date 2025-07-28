package researchersmod.powers.experiments;

import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import researchersmod.Researchers;
import researchersmod.powers.BasePower;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.util.Wiz;

public class CentrifugeExperiment extends BasePower implements InvisiblePower, NonStackablePower, ExperimentPower
{

    public static final String POWER_ID = Researchers.makeID(CentrifugeExperiment.class.getSimpleName());
    public static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURNBASED = false;
    private final AbstractCard targetCard;

    public CentrifugeExperiment(AbstractCreature owner, int amount, AbstractCard card, AbstractCard targetCard) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount, card);
        this.targetCard = targetCard;
    }

    public void terminateEffect(){
        ((AbstractPlayer) owner).limbo.addToTop(targetCard);
        targetCard.target_x = Settings.WIDTH / 2.0F;
        targetCard.target_y = Settings.HEIGHT / 2.0F;
        Wiz.att(new ExhaustSpecificCardAction(targetCard,((AbstractPlayer)owner).limbo));
        ExperimentCardManager.remExp(k,this,false,true);
    }

    public void completionEffect(){
        AbstractCard card = targetCard.makeSameInstanceOf();
        card.current_y = -200.0F * Settings.scale;
        card.target_x = Settings.WIDTH / 2.0F;
        card.target_y = Settings.HEIGHT / 2.0F;
        card.targetAngle = 0.0F;
        card.dontTriggerOnUseCard = true;
        card.applyPowers();
        addToTop((new NewQueueCardAction(card, (AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), false, true)));
        ExperimentCardManager.tickExperiment(this);
    }
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == targetCard.type) {
            ExperimentCardManager.complete(this);
        }
    }

}
