package researchersmod.cards.uncommon;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.actions.IncreaseMiscTrialAction;
import researchersmod.cards.ExperimentCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;

public class Analyze extends ExperimentCard {
    public static final String ID = makeID(Analyze.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public Analyze() {
        super(ID, info,0);
        this.misc = 1;
        setBlock(3);
        setMagic(1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new IncreaseMiscTrialAction(this.uuid,p,this,magicNumber));
    }

    public void editTrial(int trialAmount) {
        setCustomVar("Trial",trialAmount);
        initializeDescription();
    }
}
