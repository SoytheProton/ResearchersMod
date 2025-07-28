package researchersmod.cards.deprecated;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.Researchers;
import researchersmod.cardmods.PhaseMod;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.deprecated.EnthalpyPower;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

import java.util.Objects;

public class Enthalpy extends BaseCard {
    public static final String ID = makeID(Enthalpy.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public Enthalpy() {
        super(ID, info);
        setCostUpgrade(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(hasPhaseCard()) {
            addToBot(new SelectCardsInHandAction(1, cardStrings.EXTENDED_DESCRIPTION[0], false, false, (c ->
                    CardModifierManager.hasModifier(c, PhaseMod.ID) && !Objects.equals(c.cardID, this.cardID)
            ), (cards) -> {
                for (AbstractCard c : cards) {
                    if (c != null) {
                        Wiz.atb(new ExhaustSpecificCardAction(c, Wiz.p().hand));
                        addToBot(new ApplyPowerAction(p, p, new EnthalpyPower(p, c.makeStatEquivalentCopy())));
                    } else {
                        Researchers.logger.warn("Card was null.");
                    }
                }
                if (cards.isEmpty()) Researchers.logger.warn("No cards selected.");
            }));
        }
    }

    boolean hasPhaseCard() {
        boolean phase = false;
        for(AbstractCard c : Wiz.p().hand.group) {
            if(CardModifierManager.hasModifier(c,PhaseMod.ID) && !Objects.equals(c.cardID, this.cardID)) {
                phase = true;
                break;
            }
        }
        return phase;
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        }
        if (!hasPhaseCard()) {
            canUse = false;
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[1];
        }
        return canUse;
    }
}
