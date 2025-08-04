package researchersmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import researchersmod.cards.ExperimentCard;
import researchersmod.cards.uncommon.Analyze;
import researchersmod.powers.experiments.AnalyzeExperiment;
import researchersmod.util.Wiz;

import java.util.UUID;

public class IncreaseMiscTrialAction
        extends AbstractGameAction
{
    private int miscIncrease;
    private UUID uuid;
    private final AbstractPlayer p;

    private final AbstractCard c;

    public IncreaseMiscTrialAction(UUID targetUUID, AbstractPlayer p, AbstractCard c, int miscIncrease) {
        this.miscIncrease = miscIncrease;
        this.uuid = targetUUID;
        this.p = p;
        this.c = c;
    }


    public void update() {
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (!c.uuid.equals(this.uuid))
                continue;
            c.misc += this.miscIncrease;
            ((Analyze)c).editTrial(c.misc);
            c.applyPowers();
        }
        for (AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
            c.misc += this.miscIncrease;
            ((Analyze)c).editTrial(c.misc);
            c.applyPowers();
        }
        Wiz.atb(new ApplyPowerAction(p, p, new AnalyzeExperiment(p, ((ExperimentCard) c).trial, c, c.baseBlock)));
        this.isDone = true;
    }
}
