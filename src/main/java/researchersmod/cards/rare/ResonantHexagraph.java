package researchersmod.cards.rare;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cardmods.PhaseMod;
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

    private boolean hasMadeCopyThisTurn;


    public ResonantHexagraph() {
        super(ID, info);
        setBlock(12,2);
        setPhase(true);
        this.isEthereal = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new GainBlockAction(p, block));
        if (!hasMadeCopyThisTurn && this.baseBlock > 2) {
            hasMadeCopyThisTurn = true;
            AbstractCard card = this.makeStatEquivalentCopy();
            card.baseBlock -= 2;
            if(card.baseBlock <= 0)
                card.baseBlock = 0;
            Wiz.atb(new MakeTempCardInHandAction(card));
        }
    }


    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }
}
