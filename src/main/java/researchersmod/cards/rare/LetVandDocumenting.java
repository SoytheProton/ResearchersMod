package researchersmod.cards.rare;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import researchersmod.cards.BaseCard;
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
        setMagic(2,1);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int i = 0;
        for(AbstractCard c : p.hand.group)
            if(c.type == CardType.STATUS)
                i++;
        if(i > 0) {
            Wiz.applyToSelf(new StrengthPower(p, i * magicNumber));
            Wiz.applyToSelf(new DexterityPower(p, i * magicNumber));
        }
    }
}
