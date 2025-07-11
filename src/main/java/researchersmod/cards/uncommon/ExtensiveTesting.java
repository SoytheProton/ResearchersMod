package researchersmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.cards.colorless.FieldTest;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.ExtensiveTestingPower;
import researchersmod.util.CardStats;

public class ExtensiveTesting extends BaseCard {
    public static final String ID = makeID(ExtensiveTesting.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public ExtensiveTesting() {
        super(ID, info);
        setMagic(1,1);
        cardsToPreview = new FieldTest();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ExtensiveTestingPower(p,this.upgraded)));
    }

    public void upgrade() {
        if(!upgraded) {
            super.upgrade();
            cardsToPreview.upgrade();
        }
    }
}
