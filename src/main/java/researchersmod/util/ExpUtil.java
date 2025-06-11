package researchersmod.util;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.cardmods.ExperimentMod;
import researchersmod.cards.ExperimentCard;
import researchersmod.powers.BasePower;

public class ExpUtil {
    public interface onExperimentInterface {
        void onExperiment();
    }

    public interface onCompletionInterface {
        void onCompletion(AbstractPower power);
    }

    public interface onTerminateInterface {
        void onTerminate(AbstractPower power);
    }

}

