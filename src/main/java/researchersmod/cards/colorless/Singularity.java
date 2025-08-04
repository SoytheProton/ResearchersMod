package researchersmod.cards.colorless;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.ExperimentCard;
import researchersmod.powers.experiments.SingularityExperiment;
import researchersmod.util.CardStats;

public class Singularity extends ExperimentCard {
    public static final String ID = makeID(Singularity.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0
    );

    public Singularity() {
        super(ID, info, 4);
        setBlock(4,2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new SingularityExperiment(p, this.trial,this,this.baseBlock)));
    }

    public AbstractCard makeTrialCopy(int trialNumber) {
        setCustomVar("Trial",trialNumber);
        AbstractCard retVal = super.makeStatEquivalentCopy();
        setCustomVar("Trial",this.baseTrial);
        return retVal;
    }

    public AbstractCard makeStatEquivalentCopy() {
        return makeTrialCopy(this.baseTrial);
    }
}
