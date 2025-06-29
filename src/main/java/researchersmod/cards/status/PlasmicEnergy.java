package researchersmod.cards.status;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import researchersmod.Researchers;
import researchersmod.cardmods.EthericMod;
import researchersmod.cards.BaseCard;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

import java.util.ArrayList;
import java.util.List;

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
        setPhase(true);
    }
    @Override
    protected List<AbstractCardModifier> getInitialModifiers() {
        ArrayList<AbstractCardModifier> retVal = new ArrayList<>();
        EthericMod etheric = new EthericMod();
        etheric.editEtheric(3);
        retVal.add(etheric);
        return retVal;
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
            AbstractCardModifier mod = CardModifierManager.getModifiers(this, EthericMod.ID).get(0);
            ((EthericMod)mod).editEtheric(2);
        }
    }
}