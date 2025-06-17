package researchersmod.cards.status;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.ExperimentCard;
import researchersmod.powers.experiments.ServerHashExperiment;
import researchersmod.powers.experiments.ShortCircuitExperiment;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class ShortCircuit extends ExperimentCard {
    public static final String ID = makeID(ShortCircuit.class.getSimpleName());
    private static final CardStats info = new CardStats(
            AbstractCard.CardColor.COLORLESS,
            AbstractCard.CardType.STATUS,
            AbstractCard.CardRarity.SPECIAL,
            AbstractCard.CardTarget.SELF,
            0
    );

    public ShortCircuit() {
        super(ID, info, 1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ShortCircuitExperiment(p, this.Trial,this)));
    }

    public void triggerWhenDrawn() {
        Wiz.att(new UseCardAction(this,Wiz.adp()));
    }
}
