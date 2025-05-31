package researchersmod.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

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


    public static void ExperimentDescription (AbstractCard card, int amount, boolean upgraded) {
        System.out.println("amazing it ran");
        System.out.println(card);
        System.out.println(amount);
        System.out.println(upgraded);
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(card.cardID);
        card.rawDescription = "${modID}:Experiment " + amount + " - " + cardStrings.EXTENDED_DESCRIPTION[0];
        if (upgraded) card.rawDescription = "${modID}:Experiment " + amount + " - " + cardStrings.EXTENDED_DESCRIPTION[1];
        System.out.println(card.rawDescription);
        card.initializeDescription();

    }
    public static void ExperimentDescription (AbstractCard card, int amount) {
        ExperimentDescription(card,amount,false);
    }

}

