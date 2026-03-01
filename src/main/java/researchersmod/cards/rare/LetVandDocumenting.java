package researchersmod.cards.rare;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.cards.status.BurntDocument;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.LetVandDocumentingPower;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class LetVandDocumenting extends BaseCard {
    public static final String ID = makeID(LetVandDocumenting.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    public LetVandDocumenting() {
        super(ID, info);
        setMagic(1,1);
        this.cardsToPreview = new BurntDocument();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new LetVandDocumentingPower(p, this.magicNumber, 1));
    }
}
