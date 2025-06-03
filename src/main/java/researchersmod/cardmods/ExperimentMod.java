package researchersmod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import researchersmod.cards.ExperimentCard;
import researchersmod.util.ExperimentUtil;

public class ExperimentMod extends AbstractCardModifier {
    public static String ID = "researchersmod:ExperimentModifier";
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);

    @Override
    public AbstractCardModifier makeCopy() {
        return new ExperimentMod();
    }

    private int trial = 0;
    private boolean upgraded = false;

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (card instanceof ExperimentCard){
            trial = ((ExperimentCard) card).Trial;
            upgraded = ((ExperimentCard) card).shouldUpgradeDescription;
        }
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(card.cardID);
        System.out.println("Cardmod:" + trial);
        if (upgraded) return(uiStrings.TEXT[0] + trial + uiStrings.TEXT[1] + cardStrings.EXTENDED_DESCRIPTION[1]);
        return (uiStrings.TEXT[0] + trial + uiStrings.TEXT[1] + cardStrings.EXTENDED_DESCRIPTION[0]);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
