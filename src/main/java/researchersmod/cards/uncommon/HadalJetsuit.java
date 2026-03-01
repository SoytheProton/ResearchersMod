package researchersmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.actions.unique.HadalJetsuitAction;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;

public class HadalJetsuit extends BaseCard {
    public static final String ID = makeID(HadalJetsuit.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    public HadalJetsuit() {
        super(ID, info);
        setMagic(2,1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(1, new HadalJetsuitAction(this.magicNumber)));
    }
}
