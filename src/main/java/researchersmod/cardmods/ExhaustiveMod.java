package researchersmod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;

public class ExhaustiveMod extends AbstractCardModifier {
    public static String ID = "researchersmod:ExhaustiveCardModifier";
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);

    public int exhaustiveValue = 1;
    public int baseExhaustiveValue = 1;

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if(rawDescription.contains(" NL Exhaust.")){
            return rawDescription.replaceFirst(" NL Exhaust.", getReturnString(0));
        }
        else return rawDescription + getReturnString(0);
    }

    private String getReturnString(int index) {
        return uiStrings.TEXT[index];
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return true; //!card.exhaust || !CardModifierManager.hasModifier(card,ExhaustiveMod.ID)
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        this.identifier(card);
        ExhaustiveVariable.setBaseValue(card,baseExhaustiveValue);
        System.out.println("Did this do the thing");
    }

    @Override
    public void onRemove(AbstractCard card) {
        ExhaustiveVariable.setBaseValue(card, -1);
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

    public void editExhaustive(int baseValue) {
        editExhaustive(baseValue, baseValue);
    }

    public void editExhaustive(int baseValue, int value) {
        this.exhaustiveValue = value;
        this.baseExhaustiveValue = baseValue;
    }
}