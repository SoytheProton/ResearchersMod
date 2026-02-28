package researchersmod.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.actions.common.ApplyDistortionPowerToAll;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.HadalJetsuitPower;
import researchersmod.util.CardStats;

public class HadalJetsuit extends BaseCard {
    public static final String ID = makeID(HadalJetsuit.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    public HadalJetsuit() {
        super(ID, info);
        setMagic(2,1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyDistortionPowerToAll(magicNumber));
        addToBot(new ApplyPowerAction(p, p, new HadalJetsuitPower(p,1)));
    }
}
