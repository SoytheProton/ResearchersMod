package researchersmod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.powers.BasePower;
import researchersmod.powers.interfaces.DescriptionModifier;
import researchersmod.util.Wiz;

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
        String cutOff = rawDescription.substring(rawDescription.indexOf(" - ") + 1);
        String returnString =  (uiStrings.TEXT[0] + cutOff);
        AbstractPower experimentPower = null;
        for (AbstractPower p : Wiz.adp().powers) {
            if(p instanceof BasePower) {
                if (((BasePower) p).k == card)
                    experimentPower = p;
                if (p instanceof DescriptionModifier && experimentPower == ((BasePower) p).attachedPower) {
                    returnString = ((DescriptionModifier) p).ModifyDescription(returnString);
                }
            }
        }
        return returnString;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        OriginalCost = card.cost;
        card.cost = -2; // Unplayable so it looks cool or smth
        this.identifier(card);
    }

    public boolean shouldApply(AbstractCard card) {
        return (!CardModifierManager.hasModifier(card,ExperimentMod.ID));
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
