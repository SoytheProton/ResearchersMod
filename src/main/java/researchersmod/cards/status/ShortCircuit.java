package researchersmod.cards.status;

import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AutoplayField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.ExperimentCard;
import researchersmod.powers.experiments.ServerHashExperiment;
import researchersmod.powers.experiments.ShortCircuitExperiment;
import researchersmod.ui.ExperimentCardManager;
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
        AutoplayField.autoplay.set(this,true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ShortCircuitExperiment(p, this.Trial,this)));
    }
}
