package researchersmod.cards.common;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class RSquare extends BaseCard {
    public static final String ID = makeID(RSquare.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            AbstractCard.CardTarget.SELF,
            1
    );
    private int realBaseBlock;

    private int ExperimentTrialCount() {
        int i = 0;
        for (AbstractPower power : Wiz.adp().powers) {
            if (power instanceof ExperimentPower) {
                i += power.amount;
            }
        }
        return i * 2;
    }

    public RSquare() {
        super(ID, info);
        setBlock(6, 3);
        this.baseMagicNumber = 0;
        this.magicNumber = this.baseMagicNumber;
    }

    public void applyPowers() {
        this.realBaseBlock = this.baseBlock;
        this.baseMagicNumber = ExperimentTrialCount();
        this.baseBlock += baseMagicNumber;
        super.applyPowers();
        this.baseBlock = realBaseBlock;
        this.isBlockModified = (this.block != this.baseBlock);
    }

    public void calculateCardDamage(AbstractMonster mo) {
        this.realBaseBlock = this.baseBlock;
        this.baseMagicNumber = ExperimentTrialCount();
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


