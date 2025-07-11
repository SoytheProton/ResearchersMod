package researchersmod.cards.rare;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import researchersmod.cards.BaseCard;
import researchersmod.cards.status.BurntDocument;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class LetVandDocumenting extends BaseCard {
    public static final String ID = makeID(LetVandDocumenting.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public LetVandDocumenting() {
        super(ID, info);
        setExhaustive(2,1);
        this.cardsToPreview = new BurntDocument();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int i = 0;
        for(AbstractCard c : p.hand.group)
            if(c.type == CardType.STATUS)
                i++;
        Wiz.applyToSelf(new StrengthPower(p, i));
        Wiz.applyToSelf(new DexterityPower(p, i));
    }
}
