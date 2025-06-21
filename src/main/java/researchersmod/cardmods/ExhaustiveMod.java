package researchersmod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.dynamicdynamic.DynamicDynamicVariable;
import com.evacipated.cardcrawl.mod.stslib.dynamicdynamic.DynamicProvider;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import researchersmod.util.Wiz;

import java.util.UUID;

import static researchersmod.Researchers.logger;

public class ExhaustiveMod extends AbstractCardModifier implements DynamicProvider {
    public static String ID = "researchersmod:ExhaustiveCardModifier";
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);

    public final UUID uuid = UUID.randomUUID();

    public int exhaustiveValue = 1;
    public int baseExhaustiveValue = 1;

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        String key = "!" + DynamicProvider.generateKey(card, this) + "!";
        if(card.exhaust && rawDescription.contains(" NL Exhaust.")){
            return rawDescription.replaceFirst(" NL Exhaust.", getReturnString(0,key));
        }
        else return String.format(getReturnString(0,key), rawDescription);
    }

    private String getReturnString(int index, String key) {
        String uistring = uiStrings.TEXT[index];
        return uistring + " " + key + ".";
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return true; //!card.exhaust || !CardModifierManager.hasModifier(card,ExhaustiveMod.ID)
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        this.identifier(card);
        logger.info("did this do the thing");
        DynamicDynamicVariable.registerVariable(card,this);
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.exhaust = false;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        this.exhaustiveValue--;
        if(exhaustiveValue == 1)
            card.exhaust = true;
        if(exhaustiveValue == 0 && !card.exhaust) {
            card.exhaust = true;
            logger.warn("What the fuck.");
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        ExhaustiveMod mod = new ExhaustiveMod();
        mod.baseExhaustiveValue = this.baseExhaustiveValue;
        mod.exhaustiveValue = this.exhaustiveValue;
        return mod;
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
        return exhaustiveValue != baseExhaustiveValue;
    }

    @Override
    public int value(AbstractCard abstractCard) {
        return this.exhaustiveValue;
    }

    @Override
    public int baseValue(AbstractCard abstractCard) {
        return this.baseExhaustiveValue;
    }

    public void editExhaustive(int baseValue) {
        editExhaustive(baseValue, baseValue);
    }

    public void editExhaustive(int baseValue, int value) {
        this.exhaustiveValue = value;
        this.baseExhaustiveValue = baseValue;
    }
}