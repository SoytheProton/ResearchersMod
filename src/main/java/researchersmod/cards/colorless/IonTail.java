package researchersmod.cards.colorless;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.WingbeatPower;
import researchersmod.util.CardStats;

public class IonTail extends BaseCard {
    public static final String ID = makeID(IonTail.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public IonTail() {
        super(ID, info);
        setMagic(3,1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new MetallicizePower(p,magicNumber),magicNumber));
        addToBot(new ApplyPowerAction(p, p, new PlatedArmorPower(p,magicNumber),magicNumber));
    }
}
