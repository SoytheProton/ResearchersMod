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
import researchersmod.Researchers;
import researchersmod.cards.ExperimentCard;

import java.util.UUID;

public class ExperimentMod extends AbstractCardModifier {
    public static String ID = "researchersmod:ExperimentModifier";
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);

    private int OriginalCost = -2;

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
        String desc  = CardCrawlGame.languagePack.getCardStrings(card.cardID).DESCRIPTION;
        String cutOff = desc.substring(desc.indexOf(" - ") + 1);
        if (upgraded) return(uiStrings.TEXT[0] + cutOff);
        return (uiStrings.TEXT[0] + cutOff);
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        OriginalCost = card.cost;
        card.cost = -2; // Unplayable so it looks cool or smth
        this.identifier(card);
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.cost = OriginalCost;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
