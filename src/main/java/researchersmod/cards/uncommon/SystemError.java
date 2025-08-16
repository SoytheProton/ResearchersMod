package researchersmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.cards.status.ShortCircuit;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.SystemErrorPower;
import researchersmod.util.CardStats;

public class SystemError extends BaseCard {
    public static final String ID = makeID(SystemError.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public SystemError() {
        super(ID, info);
        setMagic(1);
        setCostUpgrade(0);
        this.cardsToPreview = new ShortCircuit();
        this.cardsToPreview.upgrade();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new SystemErrorPower(p,this.magicNumber)));
    }
}
