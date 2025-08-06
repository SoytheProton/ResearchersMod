package researchersmod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class ThermiteMod extends AbstractCardModifier {
    public static String ID = "researchersmod:ThermiteMod";

    @Override
    public AbstractCardModifier makeCopy() {
        return new ThermiteMod();
    }
    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card,ID);
    }
    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}