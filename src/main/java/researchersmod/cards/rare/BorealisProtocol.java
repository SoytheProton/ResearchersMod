package researchersmod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.BorealisProtocolPower;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class BorealisProtocol extends BaseCard {
    public static final String ID = makeID(BorealisProtocol.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            -2
    );

    public BorealisProtocol() {
        super(ID, info);
        setMagic(2,-1);
        setPhase(true);
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new BorealisProtocolPower(p)));
    }

    public void triggerWhenDrawn() {
        Wiz.atb(new LoseEnergyAction(this.magicNumber));
    }
}
