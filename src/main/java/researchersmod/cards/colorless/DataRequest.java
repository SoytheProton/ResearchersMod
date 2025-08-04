package researchersmod.cards.colorless;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.ExperimentCard;
import researchersmod.powers.experiments.DataRequestExperiment;
import researchersmod.util.CardStats;

public class DataRequest extends ExperimentCard {
    public static final String ID = makeID(DataRequest.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0
    );

    public DataRequest() {
        super(ID, info, 1);
        setDamage(5,3);
        setBlock(5,3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new DataRequestExperiment(p, this.trial,this,this.baseBlock,this.baseDamage)));
    }
}
