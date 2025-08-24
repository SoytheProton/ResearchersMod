package researchersmod.cards.rare;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.actions.unique.HeavyContainmentAction;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class HeavyContainment extends BaseCard {
    public static final String ID = makeID(HeavyContainment.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            3
    );

    public HeavyContainment() {
        super(ID, info);
        setCostUpgrade(2);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new HeavyContainmentAction(p,false));
    }
}
