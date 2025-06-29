package researchersmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.ParticleAcceleratorPower;
import researchersmod.powers.ResearchGrantsPower;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class ParticleAccelerator extends BaseCard {
    public static final String ID = makeID(ParticleAccelerator.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            -2
    );

    public ParticleAccelerator() {
        super(ID, info);
        setMagic(0,2);
        setPhase(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ParticleAcceleratorPower(p,1),1));
        if(upgraded)
            Wiz.atb(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, magicNumber),magicNumber));
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }
}
