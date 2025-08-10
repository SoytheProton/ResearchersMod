package researchersmod.cards.colorless;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.ExperimentCard;
import researchersmod.powers.experiments.DataOverrideExperiment;
import researchersmod.util.CardStats;

public class DataOverride extends ExperimentCard {
    public static final String ID = makeID(DataOverride.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0
    );

    public DataOverride() {
        super(ID, info, 1);
        setDamage(3,2);
        setBlock(3,2);
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new DataOverrideExperiment(p, this.trial,this)));
    }
}
