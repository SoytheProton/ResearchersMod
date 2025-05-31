package researchersmod.deprecated.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;

public class Adapt extends BaseCard {
    public static final String ID = makeID(Adapt.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            AbstractCard.CardTarget.SELF,
            1
    );
    public Adapt() {
        super(ID, info);
    }

    private boolean HandCheck() {
        return AbstractDungeon.player.hand.size() < 4;
    }
    public void applyPowers() {
        if (HandCheck()) {
            this.rawDescription =  cardStrings.EXTENDED_DESCRIPTION[1] + " NL " + cardStrings.EXTENDED_DESCRIPTION[2];
            initializeDescription();
        } else {
            this.rawDescription =  cardStrings.EXTENDED_DESCRIPTION[0] + " NL " + cardStrings.EXTENDED_DESCRIPTION[3];
            initializeDescription();
        }
        super.applyPowers();
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    public void triggerOnEndOfPlayerTurn() {
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (HandCheck()) {
            addToBot(new DrawCardAction(2));
        } else {
            addToBot(new ExhaustAction(1, false));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            super.upgrade();
            upgradeBaseCost(0);
        }
    }
}

