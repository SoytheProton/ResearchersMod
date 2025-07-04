package researchersmod.util;

import basemod.cardmods.EtherealMod;
import basemod.cardmods.InnateMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import researchersmod.cardmods.BetterEtherealMod;
import researchersmod.cardmods.EthericMod;
import researchersmod.cardmods.PhaseMod;

import java.util.Arrays;

/* Keyword Order
Unplayable.
Phase. Innate.
 */

public class KH {
    public static boolean hasEthereal(AbstractCard card, String rawDescription) {
        if(card.isEthereal) return true;
        if(CardModifierManager.hasModifier(card, BetterEtherealMod.ID)) return true;
        if(CardModifierManager.hasModifier(card, EtherealMod.ID)) return true;
        if(rawDescription.contains("Ethereal. NL ")) return true;
        if(rawDescription.contains("Ethereal. ")) return true;
        return false;
    }

    public static boolean hasInnate(AbstractCard card, String rawDescription) {
        if(card.isInnate) return true;
        if(CardModifierManager.hasModifier(card, InnateMod.ID)) return true;
        if(rawDescription.contains("Innate. NL ")) return true;
        if(rawDescription.contains("Innate. ")) return true;
        return false;
    }

    public static boolean hasEtheric(AbstractCard card, String rawDescription) {
        if(CardModifierManager.hasModifier(card, EthericMod.ID)) return true;
        if(rawDescription.contains("Etheric.")) return true;
        return false;
    }

    public static boolean hasEther(AbstractCard card, String rawDescription) {
        return hasEtheric(card, rawDescription) || hasEthereal(card, rawDescription);
    }

    public static boolean hasPhase(AbstractCard card, String rawDescription) {
        if(CardModifierManager.hasModifier(card, PhaseMod.ID)) return true;
        if(rawDescription.contains("Phase. NL ")) return true;
        if(rawDescription.contains("Phase.")) return true;
        return false;
    }

    public static boolean hasUnplayable(AbstractCard card, String rawDescription) {
        if(rawDescription.contains("Unplayable. NL ") && card.cost == -2) return true;
        return false;
    }

    public static boolean hasUnplayableNL(AbstractCard card, String rawDescription) {
        if(hasUnplayable(card, rawDescription)) return false;
        if(rawDescription.contains("Unplayable.") && card.cost == -2) return true;
        return false;
    }

    public static boolean hasRetain(AbstractCard card, String rawDescription) {
        if(card.selfRetain) return true;
        if(rawDescription.contains("Retain. NL ")) return true;
        if(rawDescription.contains("Retain.")) return true;
        return false;
    }

    public static String[] autoString(String index, String indexStart,String rawDescription) {
        int i = rawDescription.indexOf(index,rawDescription.indexOf(indexStart) + indexStart.length());
        if(index.equals(LocalizedStrings.PERIOD)) i++;
        if (i == -1) i = 0;
        // System.out.println(Arrays.toString(new String[]{index, indexStart, rawDescription, String.valueOf(i)}));
        return new String[]{rawDescription.substring(0, i), rawDescription.substring(i)};
    }

}
