package researchersmod.powers.experiments;

import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import researchersmod.Researchers;
import researchersmod.patches.OnBlockDamagePatch;
import researchersmod.powers.BasePower;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.ui.ExperimentCardManager;

public class DocumentationExperiment extends BasePower implements InvisiblePower, NonStackablePower, ExperimentPower, OnBlockDamagePatch.OnBlockDamageInterface {

    public static final String POWER_ID = Researchers.makeID(DocumentationExperiment.class.getSimpleName());
    public static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURNBASED = false;
    public DocumentationExperiment(AbstractCreature owner, int amount, AbstractCard card) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount,card);
    }

    public void terminateEffect(){
        ExperimentCardManager.removeExperiment(expCard(),this, AbstractDungeon.player.hand,false);
    }

    public void completionEffect(){
        ExperimentCardManager.tickExperiment(this);
    }

    @Override
    public void onDamageBlocked(int blockedDamage) {
        if(blockedDamage >= expCard().magicNumber) {
            ExperimentCardManager.complete(this);
            expCard().magicNumber = expCard().baseMagicNumber;
            expCard().isMagicNumberModified = false;
        } else {
            expCard().magicNumber -= blockedDamage;
            expCard().isMagicNumberModified = true;
        }
    }
}
