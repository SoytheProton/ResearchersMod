package researchersmod.cards.deprecated;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;

public class OldDatamining extends BaseCard {
    public static final String ID = makeID(OldDatamining.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );
    public OldDatamining() {
        super(ID, info);
    }

    private boolean hasTurnEnded = false;

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    public void triggerOnEndOfPlayerTurn() {
        hasTurnEnded = true;
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
        super.triggerOnEndOfPlayerTurn();
    }

    public void atTurnStart() {
        super.atTurnStart();
        hasTurnEnded = false;
    }

    public void applyPowers() {
        if (CardTypesPlayed(null) > 0 && !hasTurnEnded) {
            super.applyPowers();
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0] + CardTypesPlayed(null) + cardStrings.EXTENDED_DESCRIPTION[1];
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(CardTypesPlayed(null)));
    }

    public void upgrade() {
        if (!this.upgraded) {
            super.upgrade();
            upgradeBaseCost(0);
        }
    }
}
