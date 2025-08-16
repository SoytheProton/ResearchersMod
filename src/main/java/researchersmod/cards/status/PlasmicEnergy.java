package researchersmod.cards.status;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import researchersmod.cards.BaseCard;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;
import researchersmod.variables.EthericVariable;

public class PlasmicEnergy extends BaseCard {
    public static final String ID = makeID(PlasmicEnergy.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.STATUS,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            -2
    );

    public PlasmicEnergy() {
        super(ID, info);
        this.exhaust = true;
        setMagic(1);
        EthericVariable.setBaseValue(this,2);
        setPhase(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new ApplyPowerAction(p, p, new EnergizedBluePower(p,1)));
        Wiz.atb(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, magicNumber)));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded) {
            EthericVariable.upgrade(this,-3);
            super.upgrade();
            this.isEthereal = true;
        }
    }
}