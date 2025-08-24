package researchersmod.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.actions.unique.IncreaseMiscTrialAction;
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
        this.misc = 0;
        setBlock(2,1);
        setMagic(1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new IncreaseMiscTrialAction(this.uuid,p,this,magicNumber));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        setCustomVar("Trial",this.misc);
    }

    public void editTrial(int trialAmount) {
        setCustomVar("Trial",trialAmount);
        this.baseTrial = trialAmount;
        this.trial = trialAmount;
        initializeDescription();
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        return makeTrialCopy(this.baseTrial);
    }
}
