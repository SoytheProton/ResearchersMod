package researchersmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.VolcanicResearchPower;
import researchersmod.powers.WingbeatPower;
import researchersmod.util.CardStats;

public class VolcanicResearch extends BaseCard {
    public static final String ID = makeID(VolcanicResearch.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public VolcanicResearch() {
        super(ID, info);
        setMagic(1,1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new VolcanicResearchPower(p,magicNumber),magicNumber));
    }
}
