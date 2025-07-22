package researchersmod.cards.rare;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.OwnTempoPower;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class OwnTempo extends BaseCard {
    public static final String ID = makeID(OwnTempo.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );


    public OwnTempo() {
        super(ID, info);
        setMagic(1,1);
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new OwnTempoPower(p,this.magicNumber));

    }
}
