package researchersmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.actions.common.CompletionAction;
import researchersmod.fields.ExperimentPowerFields;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.util.Wiz;

public class FinalizeAction extends AbstractGameAction {
    private final AbstractPlayer p;

    public FinalizeAction(AbstractPlayer p) {
        this.p = p;
        this.duration = Settings.ACTION_DUR_XFAST;
    }
    @Override
    public void update() {
        int i = ExperimentCardManager.experiments.group.size();
        for(AbstractCard c : ExperimentCardManager.experiments.group) {
            AbstractPower targetPower = null;
            for (AbstractPower power : p.powers) {
                if (power instanceof ExperimentPower && ExperimentPowerFields.attachedCard.get(power) == c) {
                    targetPower = power;
                }
            }
            if (targetPower != null) {
                Wiz.atb(new CompletionAction(targetPower, true));
            }
            else
                Researchers.logger.warn("Target Power is null.");
        }
        if(i > 0) Wiz.atb(new DrawCardAction(i));
        this.isDone = true;
    }
}
