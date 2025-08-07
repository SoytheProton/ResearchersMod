package researchersmod.cards.deprecated;
/*
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;

public class Older extends BaseCard {

    public static final String ID = makeID(Older.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            0
    );

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = -1;
    public Older() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);
    }

    private boolean hasTurnEnded = false;
    public void triggerOnGlowCheck() {
        if (CardTypesPlayed(null) >= this.magicNumber) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
        super.onMoveToDiscard();
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
        if (CardTypesPlayed(null) > this.magicNumber) {
            addToBot(new GainEnergyAction(1));
        }
    }

}
*/