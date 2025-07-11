package researchersmod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.EclipsedFormPower;
import researchersmod.util.CardStats;

public class EclipsedForm extends BaseCard {
    public static final String ID = makeID(EclipsedForm.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            3
    );

    public EclipsedForm() {
        super(ID, info);
        setMagic(1);
        this.isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new EclipsedFormPower(p),magicNumber));
    }

    public void upgrade() {
        if(!upgraded) {
            super.upgrade();
            this.isEthereal = false;
        }
    }
}
