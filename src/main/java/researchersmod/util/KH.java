package researchersmod.util;

import basemod.cardmods.EtherealMod;
import basemod.cardmods.InnateMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import researchersmod.cardmods.BetterEtherealMod;
import researchersmod.cardmods.EthericMod;
import researchersmod.cardmods.PhaseMod;
import researchersmod.ui.ModConfig;

import java.util.Objects;
import java.util.regex.Pattern;

/* Keyword Order
Unplayable.
Phase. Innate. Ethereal. Retain/Ethereal.
DESCRIPTION.
Exhaust.
 */

public class KH {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("researchersmod:Keywords");

    public static boolean hasEthereal(AbstractCard card, String rawDescription) {
        if(hasEtheric(card,rawDescription)) return false;
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
        if(CardModifierManager.hasModifier(card, EthericMod.ID)) {
            return true;
        }
        return false;
    }

    public static boolean hasEther(AbstractCard card, String rawDescription) {
        return hasEtheric(card, rawDescription) || hasEthereal(card, rawDescription);
    }

    public static boolean hasPhase(AbstractCard card, String rawDescription) {
        if(CardModifierManager.hasModifier(card, PhaseMod.ID)) return true;
        if(rawDescription.contains("Phase. NL ")) return true;
        if(rawDescription.contains("Phase.")) return true;
        if(Pattern.compile("Phase\\s+([0-9]+)").matcher(rawDescription).find()) return true;
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
        int y = rawDescription.indexOf(indexStart) + indexStart.length();
        if(indexStart.equals(uiStrings.TEXT[5])) y += 2;
        if(Objects.equals(index, "${modID}:Phase") && ModConfig.enablePhaseNumbers && Pattern.compile("Phase\\s+([0-9]+)").matcher(rawDescription).find()) y = Pattern.compile("Phase\\s+([0-9]+)").matcher(rawDescription).end();
        int i = rawDescription.indexOf(index,y);
        if(index.equals(LocalizedStrings.PERIOD) || index.equals(LocalizedStrings.PERIOD + " ")) i++;
        if (i == -1 || !rawDescription.contains(indexStart)) i = 0;
        // System.out.println(Arrays.toString(new String[]{index, indexStart, rawDescription, String.valueOf(i)}));
        return new String[]{rawDescription.substring(0, i), rawDescription.substring(i)};
    }
}
