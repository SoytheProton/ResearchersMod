package researchersmod.cards.rare;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
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
        setBlock(12,2);
        setPhase(true);
        this.isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new GainBlockAction(p, block));
        this.baseBlock -= 2;
        if(this.baseBlock <= 0) this.baseBlock = 0;
        Wiz.atb(new MakeTempCardInHandAction(this.makeStatEquivalentCopy()));
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }
}
