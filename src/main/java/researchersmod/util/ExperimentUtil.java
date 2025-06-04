package researchersmod.util;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.cardmods.ExperimentMod;
import researchersmod.cards.ExperimentCard;

public class ExperimentUtil {
    public interface onExperimentInterface {
        void onExperiment(AbstractPower power);
    }

    public interface onCompletionInterface {
        void onCompletion(AbstractPower power);
    }

    public interface onTerminateInterface {
        void onTerminate(AbstractPower power);
    }


    public static void ExperimentDescription(AbstractCard card, int amount) {
        if(card instanceof ExperimentCard) {
            ((ExperimentCard) card).Trial = amount;
            System.out.println("Trial:" + ((ExperimentCard) card).Trial);
        }
        CardModifierManager.removeModifiersById(card, ExperimentMod.ID,true);
        CardModifierManager.addModifier(card, new ExperimentMod());
//        AbstractCardModifier Mod = CardModifierManager.getModifiers(card, "researchersmod:ExperimentModifier").get(0);
//        Mod.modifyDescription(card.rawDescription,card);
    }

}

