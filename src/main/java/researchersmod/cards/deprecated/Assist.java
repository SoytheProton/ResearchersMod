package researchersmod.cards.deprecated;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.deprecated.ComboPower;
import researchersmod.util.CardStats;

public class Assist extends BaseCard {
    public static final String ID = makeID(Assist.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );


    private static final int B = 6;
    private static final int UB = 3;

    private static final int M = 1;
    public Assist() {
        super(ID, info);
        setBlock(B,UB);
        setMagic(M);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ComboPower(p, this.block, this.magicNumber, this.upgraded),1));
    }
}
