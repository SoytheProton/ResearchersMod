package researchersmod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

public class ExhaustiveMod extends AbstractCardModifier {
    public static String ID = "researchersmod:ExhaustiveCardModifier";
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);

    public int exhaustiveValue = 1;
    public int baseExhaustiveValue = 1;

    private boolean inherent;

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if(rawDescription.contains(" NL Exhaust.")){
            return rawDescription.replaceFirst(" NL Exhaust.", uiStrings.TEXT[0]);
        }
        else return rawDescription + uiStrings.TEXT[0];
    }

    public boolean isInherent(AbstractCard card) {
        return inherent;
    }

    public ExhaustiveMod() {
        this(false);
    }

    public ExhaustiveMod(boolean isInherent) {
        this(isInherent,1);
    }

    public ExhaustiveMod(boolean isInherent,int exhaustiveValue) {
        this.inherent = isInherent;
        editExhaustive(exhaustiveValue);
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return !card.exhaust || !CardModifierManager.hasModifier(card,ExhaustiveMod.ID);
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        this.identifier(card);
        ExhaustiveVariable.setBaseValue(card,baseExhaustiveValue);
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