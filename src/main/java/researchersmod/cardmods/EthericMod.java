package researchersmod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CardModifierPatches;
import com.evacipated.cardcrawl.mod.stslib.dynamicdynamic.DynamicProvider;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import researchersmod.Researchers;
import researchersmod.util.Wiz;

import java.util.ArrayList;
import java.util.UUID;

public class EthericMod extends AbstractCardModifier implements DynamicProvider {
    public static String ID = "researchersmod:EthericCardModifier";
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public final UUID uuid = UUID.randomUUID();
    public static ArrayList<AbstractCardModifier> modifiers(AbstractCard c) {
        return CardModifierPatches.CardModifierFields.cardModifiers.get(c);
    }

    public int ethericValue = 1;
    public int baseEthericValue = 1;

    @Override
    public boolean shouldApply(AbstractCard card) {
        return !card.isEthereal;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        String key = "!" + DynamicProvider.generateKey(card, this) + "!";
        int addNL = 0;
        if (card.retain) addNL = 1;
        if (rawDescription.contains("Ethereal. ") && card.isEthereal) {
            String cardDescription = rawDescription.replaceFirst("Ethereal.", getReturnString(2,key));
            return String.format(cardDescription);
        }
        if (rawDescription.contains("Phase. ") && card.hasTag(Researchers.PHASE)) {
            int i = rawDescription.indexOf("Phase. ");
            String[] cardDescription = {rawDescription.substring(0, i),rawDescription.substring(i+1)};
            return String.format(getReturnString(2,key), cardDescription[0] + "${researchersmod}Phase.", cardDescription[1]);
        }
        if (rawDescription.contains("Innate. ") && card.isInnate) {
            String[] cardDescription = rawDescription.split("Innate. ", 2);
            return String.format(getReturnString(2,key), cardDescription[0] + "Innate.", cardDescription[1]);
        } else if (rawDescription.contains("Unplayable. NL ") && card.cost == -2) {
            String[] cardDescription = rawDescription.split("(Unplayable\\..*?) NL ", 2);
            return String.format(getReturnString(3+addNL,key), cardDescription[0] + "Unplayable.", cardDescription[1]);
        } else if (card.cost == -2) {
            return String.format(getReturnString(5,key), rawDescription);
        } else return String.format(getReturnString(addNL,key), rawDescription);
    }

    private String getReturnString(int index, String key) {
        String uistring = uiStrings.TEXT[index];
        String returnString = uistring.substring(0,uistring.indexOf("Etheric") + 7) + " " + ethericValue + ".";
        return returnString + uistring.substring(uistring.indexOf("Etheric") + 7);
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        this.identifier(card);
    }

    public void atEndOfTurn(AbstractCard card, CardGroup group) {
        this.ethericValue = ethericValue - 1;
        if(ethericValue == 0) {
            card.isEthereal = true;
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new EthericMod();
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.isEthereal = false;
    }


    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public UUID getDynamicUUID() {
        return uuid;
    }

    @Override
    public boolean isModified(AbstractCard abstractCard) {
        return false;
    }

    @Override
    public int value(AbstractCard abstractCard) {
        return this.ethericValue;
    }

    @Override
    public int baseValue(AbstractCard abstractCard) {
        return this.baseEthericValue;
    }
    public void editEtheric(int baseValue) {
        editEtheric(baseValue, baseValue);
    }

    public void editEtheric(int baseValue, int value) {
        this.ethericValue = value;
        this.baseEthericValue = baseValue;
    }
}

