package researchersmod.util;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.cardmods.ExperimentMod;
import researchersmod.cards.ExperimentCard;
import researchersmod.powers.BasePower;

public class ExpUtil {
    public interface onExperimentInterface {
        void onExperiment(AbstractPower power);
    }

    public interface onCompletionInterface {
        void onCompletion(AbstractPower power);
    }

    public interface onTerminateInterface {
        void onTerminate(AbstractPower power);
    }

    public static void tickExperiment(int amt, AbstractPower p) {
        AbstractCard c = ((BasePower) p).k;
        if(!CardModifierManager.hasModifier(c, ExperimentMod.ID)) {
            CardModifierManager.addModifier(c, new ExperimentMod());
        }
        p.amount = p.amount - amt;
        ((ExperimentCard) c).Trial = p.amount;
    }

    public static void tickExperiment(AbstractPower p) {
        tickExperiment(0,p);
    }

}

