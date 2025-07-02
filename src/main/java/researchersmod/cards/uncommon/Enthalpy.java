package researchersmod.cards.uncommon;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cardmods.PhaseMod;
import researchersmod.cards.BaseCard;
import researchersmod.cards.status.BurntDocument;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.EnthalpyPower;
import researchersmod.powers.EntropyPower;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class Enthalpy extends BaseCard {
    public static final String ID = makeID(Enthalpy.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public Enthalpy() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(1,cardStrings.EXTENDED_DESCRIPTION[0],false,false,(c ->
            CardModifierManager.hasModifier(c, PhaseMod.ID)
        ),(cards) -> {
            for (AbstractCard c : cards) {
                if (c != null) {
                    Wiz.atb(new ExhaustSpecificCardAction(c, Wiz.p().hand));
                    addToBot(new ApplyPowerAction(p, p, new EnthalpyPower(p, this.upgraded, c.makeStatEquivalentCopy())));
                }
            }
        }));

    }

    boolean hasPhaseCard() {
        boolean phase = false;
        for(AbstractCard c : Wiz.p().hand.group) {
            if(CardModifierManager.hasModifier(c,PhaseMod.ID)) {
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
