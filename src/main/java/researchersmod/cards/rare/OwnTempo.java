package researchersmod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.actions.common.BetterSelectCardsAction;
import researchersmod.actions.common.PhaseCardAction;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class OwnTempo extends BaseCard {
    public static final String ID = makeID(OwnTempo.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );


    public OwnTempo() {
        super(ID, info);
        setMagic(1,1);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        String plural = cardStrings.EXTENDED_DESCRIPTION[0];
        if(upgraded) plural = cardStrings.EXTENDED_DESCRIPTION[1];
        Wiz.atb(new BetterSelectCardsAction(p.discardPile.group,magicNumber, plural,false,(c -> true),(cards) -> {
            for (AbstractCard c : cards) {
                Wiz.att(new ExhaustSpecificCardAction(c, p.discardPile));
                Wiz.att(new PhaseCardAction(c));
            }
        }));
    }
}
