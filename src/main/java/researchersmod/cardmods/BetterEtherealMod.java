package researchersmod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CardModifierPatches;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

public class BetterEtherealMod extends AbstractCardModifier {
    public static String ID = "researchersmod:EtherealCardModifier";
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static ArrayList<AbstractCardModifier> modifiers(AbstractCard c) {
        return CardModifierPatches.CardModifierFields.cardModifiers.get(c);
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return !card.isEthereal;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        int addNL = 0;
        if (card.retain) addNL = 1;
        if (rawDescription.contains("Phase. ")) {
            int i = rawDescription.lastIndexOf("Phase. ");
            String[] cardDescription = {rawDescription.substring(0, i),rawDescription.substring(i+1)};
            return String.format(uiStrings.TEXT[2], cardDescription[0] + "Phase.", cardDescription[1]);
        }
        if (rawDescription.contains("Innate. ") && card.isInnate) {
            String[] cardDescription = rawDescription.split("Innate. ", 2);
            return String.format(uiStrings.TEXT[2], cardDescription[0] + "Innate.", cardDescription[1]);
        } else if (rawDescription.contains("Unplayable. NL ") && card.cost == -2) {
            String[] cardDescription = rawDescription.split("(Unplayable\\..*?) NL ", 2);
            return String.format(uiStrings.TEXT[3 + addNL], cardDescription[0] + "Unplayable.", cardDescription[1]);
        } else if (card.cost == -2) {
            return String.format(uiStrings.TEXT[5], rawDescription);
        } else return String.format(uiStrings.TEXT[addNL], rawDescription);
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.isEthereal = true;
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.isEthereal = false;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new BetterEtherealMod();
    }


    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
