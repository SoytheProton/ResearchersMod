package researchersmod.cards.deprecated;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.cardmods.PhaseMod;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.InfluxPowerPower;
import researchersmod.util.CardStats;

public class IonSurge extends BaseCard {
    public static final String ID = makeID(IonSurge.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.POWER,
            AbstractCard.CardRarity.RARE,
            AbstractCard.CardTarget.SELF,
            -2
    );

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 0;

    public IonSurge() {
        super(ID, info);
        setMagic(MAGIC,UPG_MAGIC);
        CardModifierManager.addModifier(this, new PhaseMod());
        this.isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new InfluxPowerPower(p, this.magicNumber), this.magicNumber));
    }
    public void upgrade() {
        if (!this.upgraded) {
            super.upgrade();
            upgradeBaseCost(0);
        }
    }

}