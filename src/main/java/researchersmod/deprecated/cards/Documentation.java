package researchersmod.deprecated.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.deprecated.powers.DocumentationPower;
import researchersmod.util.CardStats;

public class Documentation extends BaseCard {
    public static final String ID = makeID(Documentation.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.POWER,
            CardRarity.UNCOMMON,
            AbstractCard.CardTarget.SELF,
            1
    );

    private static final int M = 5;
    private static final int UM = 3;

    public Documentation() {
        super(ID, info);
        setMagic(M, UM);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new DocumentationPower(p, this.block)));
    }
}

