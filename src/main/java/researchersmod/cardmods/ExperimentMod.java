package researchersmod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.evacipated.cardcrawl.mod.stslib.dynamicdynamic.DynamicDynamicVariable;
import com.evacipated.cardcrawl.mod.stslib.dynamicdynamic.DynamicProvider;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import researchersmod.cards.ExperimentCard;

import java.util.UUID;

public class ExperimentMod extends AbstractCardModifier implements DynamicProvider {
    public static String ID = "researchersmod:ExperimentModifier";
    public final UUID uuid = UUID.randomUUID();
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);

    @Override
    public AbstractCardModifier makeCopy() {
        return new ExperimentMod();
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        String key = "!" + DynamicProvider.generateKey(card, this) + "!";
        boolean upgraded = false;
        if (card instanceof ExperimentCard){
            upgraded = ((ExperimentCard) card).shouldUpgradeDescription;
        }
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(card.cardID);
        if (upgraded) return(uiStrings.TEXT[0] + key + uiStrings.TEXT[1] + cardStrings.EXTENDED_DESCRIPTION[1]);
        return (uiStrings.TEXT[0] + key + uiStrings.TEXT[1] + cardStrings.EXTENDED_DESCRIPTION[0]);
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((ExperimentCard) card).Trial;
    }

    @Override
    public int value(AbstractCard card) {
        return ((ExperimentCard) card).Trial;
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return false;
    }

    @Override
    public UUID getDynamicUUID() {
        return uuid;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        DynamicDynamicVariable.registerVariable(card, this);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
