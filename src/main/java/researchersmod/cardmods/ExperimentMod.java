package researchersmod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.dynamicdynamic.DynamicDynamicVariable;
import com.evacipated.cardcrawl.mod.stslib.dynamicdynamic.DynamicProvider;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import researchersmod.cards.ExperimentCard;

import java.util.UUID;

public class ExperimentMod extends AbstractCardModifier {
    public static String ID = "researchersmod:ExperimentModifier";
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);

    @Override
    public AbstractCardModifier makeCopy() {
        return new ExperimentMod();
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        boolean upgraded = false;
        if (card instanceof ExperimentCard){
            upgraded = ((ExperimentCard) card).shouldUpgradeDescription;
        }
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(card.cardID);
        if (upgraded) return(uiStrings.TEXT[0] + cardStrings.EXTENDED_DESCRIPTION[1]);
        return (uiStrings.TEXT[0] + cardStrings.EXTENDED_DESCRIPTION[0]);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
