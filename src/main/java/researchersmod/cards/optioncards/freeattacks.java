package researchersmod.cards.optioncards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.actions.makeCardTypeFree;
import researchersmod.util.CardStats;

public class freeattacks extends BaseCard {
    public static final String ID = makeID(freeattacks.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.NONE,
            -2
    );

    public freeattacks() {
        super(ID, info);
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        onChoseThisOption();
    }
    public void onChoseThisOption() {
        addToBot(new makeCardTypeFree(CardType.ATTACK));
    }
}

