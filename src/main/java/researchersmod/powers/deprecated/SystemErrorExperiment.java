package researchersmod.powers.deprecated;

import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.cards.status.BurntDocument;
import researchersmod.powers.BasePower;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.ui.ExperimentPowerFields;
import researchersmod.util.Wiz;

import java.util.Objects;
@SuppressWarnings("all")
public class SystemErrorExperiment extends BasePower implements InvisiblePower, NonStackablePower, ExperimentPower {

    public static final String POWER_ID = Researchers.makeID(SystemErrorExperiment.class.getSimpleName());
    public static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURNBASED = false;
    public SystemErrorExperiment(AbstractCreature owner, int amount, AbstractCard card) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount,card);
    }

    public void terminateEffect(){
        if(ExperimentPowerFields.attachedCard.get(this).upgraded) {
            for(AbstractCard c : Wiz.p().hand.group) {
                if(Objects.equals(c.cardID, BurntDocument.ID)) {
                    c.upgrade();
                }
            }
        }
        ExperimentCardManager.remExp(this);
    }

    public void completionEffect(){
        Wiz.atb(new MakeTempCardInHandAction(new BurntDocument()));
        ExperimentCardManager.tickExperiment(this);
    }

    public void onCardDraw(AbstractCard card) {
        if(card.type == AbstractCard.CardType.STATUS) {
            completionEffect();
        }
    }
}
