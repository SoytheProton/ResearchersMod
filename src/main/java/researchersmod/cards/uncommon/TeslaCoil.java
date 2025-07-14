package researchersmod.cards.uncommon;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.cards.status.PlasmicEnergy;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.EnergyDownPower;
import researchersmod.powers.TeslaCoilPower;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class TeslaCoil extends BaseCard {
    public static final String ID = makeID(TeslaCoil.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public TeslaCoil() {
        super(ID, info);
        setMagic(2);
        setInnate(false, true);
        this.cardsToPreview = new PlasmicEnergy();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new EnergyDownPower(p,1,false,this.name));
        Wiz.applyToSelf(new TeslaCoilPower(p, magicNumber));
    }
}
