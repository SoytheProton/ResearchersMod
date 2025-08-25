package researchersmod.cards.rare;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class ResonantHexagraph extends BaseCard {
    public static final String ID = makeID(ResonantHexagraph.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            -2
    );


    public ResonantHexagraph() {
        super(ID, info);
        setBlock(10,2);
        setMagic(5,1);
        setPhase(true);
        setCustomVar("counter",VariableType.MAGIC,baseMagicNumber,magicUpgrade,(card, m, base) -> (card.magicNumber));
        this.isEthereal = true;
        this.isMagicNumberModified = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new GainBlockAction(p, block));
    }

    public void triggerOnExhaust() {
        if(this.magicNumber > 1) {
            AbstractCard card = this.makeStatEquivalentCopy();
            card.baseBlock -= 2;
            if (card.baseBlock <= 0)
                card.baseBlock = 0;
            card.magicNumber = this.magicNumber - 1;
            Wiz.atb(new MakeTempCardInHandAction(card));
        }
    }

    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard retVal = super.makeStatEquivalentCopy();
        retVal.magicNumber = this.magicNumber;
        return retVal;
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }
}
