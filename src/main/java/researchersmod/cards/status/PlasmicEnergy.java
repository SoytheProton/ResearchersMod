package researchersmod.cards.status;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import researchersmod.cardmods.BetterEtherealMod;
import researchersmod.cardmods.EthericMod;
import researchersmod.cards.BaseCard;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

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
        setEtheric(2);
        setPhase(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new ApplyPowerAction(p, p, new EnergizedBluePower(p,1)));
        Wiz.atb(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, magicNumber)));
    }

    @Override
    public void upgrade() {
        super.upgrade();
        if(!this.upgraded) {
            CardModifierManager.removeModifiersById(this,EthericMod.ID,true);
            CardModifierManager.addModifier(this,new BetterEtherealMod());
        }
    }
}