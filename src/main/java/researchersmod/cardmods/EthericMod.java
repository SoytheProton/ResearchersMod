package researchersmod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CardModifierPatches;
import com.evacipated.cardcrawl.mod.stslib.dynamicdynamic.DynamicProvider;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.evacipated.cardcrawl.mod.stslib.dynamicdynamic.DynamicDynamicVariable;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import researchersmod.Researchers;
import researchersmod.util.KH;
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
        return (!card.isEthereal || !CardModifierManager.hasModifier(card,EthericMod.ID));
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        String key = "!" + DynamicProvider.generateKey(card, this) + "!";
        String p = LocalizedStrings.PERIOD;
        String[] cardDescription = KH.autoString( KH.hasPhase(card,rawDescription) ? " NL " :
                        KH.hasInnate(card,rawDescription) || KH.hasUnplayableNL(card,rawDescription) ? "." :
                                KH.hasUnplayable(card,rawDescription) ? " " : "" ,
                KH.hasPhase(card, rawDescription) ? uiStrings.TEXT[1] :
                        KH.hasInnate(card, rawDescription) ? uiStrings.TEXT[4] :
                                KH.hasUnplayable(card, rawDescription) ? uiStrings.TEXT[0] + p + " NL" :
                                        KH.hasUnplayableNL(card, rawDescription) ? uiStrings.TEXT[0] : "",
                rawDescription);
        return cardDescription[0] +
                (KH.hasUnplayableNL(card,rawDescription) ? " NL " : " ")
                + uiStrings.TEXT[5] + " " + key + p +
                (KH.hasInnate(card, rawDescription) || KH.hasPhase(card, rawDescription) || KH.hasRetain(card, rawDescription) ? " " : " NL ")
                + cardDescription[1];
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        this.identifier(card);
        DynamicDynamicVariable.registerVariable(card,this);
    }

    public void atEndOfTurn(AbstractCard card, CardGroup group) {
        if(group == Wiz.adp().hand) {
            this.ethericValue = ethericValue - 1;
            if (ethericValue == 0) {
                card.isEthereal = true;
            }
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        EthericMod cardMod = new EthericMod();
        cardMod.baseEthericValue = baseEthericValue;
        cardMod.ethericValue = ethericValue;
        return cardMod;
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
        return ethericValue != baseEthericValue;
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

