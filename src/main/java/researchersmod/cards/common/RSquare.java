package researchersmod.cards.common;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;

public class RSquare extends BaseCard {
    public static final String ID = makeID(RSquare.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            AbstractCard.CardTarget.SELF,
            1
    );

    private static final int BLOCK = 4;
    private static final int UPG_BLOCK = 3;
    private int realBaseBlock;

    private int StatusesinDiscard() {
        int i = 0;
        for (AbstractCard card : AbstractDungeon.player.exhaustPile.group) {
            if (card.type == CardType.STATUS) {
                i++;
            }
        }
        return i;
    }

    public RSquare() {
        super(ID, info);
        setBlock(BLOCK, UPG_BLOCK);
        this.baseMagicNumber = 0;
        this.magicNumber = this.baseMagicNumber;
    }

    public void applyPowers() {
        this.realBaseBlock = this.baseBlock;
        this.baseMagicNumber = StatusesinDiscard();
        this.baseBlock += baseMagicNumber;
        super.applyPowers();
        this.baseBlock = realBaseBlock;
        this.isBlockModified = (this.block != this.baseBlock);
    }

    public void calculateCardDamage(AbstractMonster mo) {
        this.realBaseBlock = this.baseBlock;
        this.baseMagicNumber = StatusesinDiscard();
        this.baseBlock += baseMagicNumber;
        super.calculateCardDamage(mo);
        this.baseBlock = realBaseBlock;
        this.isBlockModified = (this.block != this.baseBlock);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.block += this.magicNumber;
        calculateCardDamage(m);
        addToBot(new GainBlockAction(p, p, this.block));
    }
}


