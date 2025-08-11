package researchersmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.ui.ExperimentPowerFields;
import researchersmod.util.Wiz;

public class FinalizeAction extends AbstractGameAction {
    private final AbstractCard target;
    private final AbstractPlayer p;
    private final boolean upgraded;

    public FinalizeAction(AbstractPlayer p, AbstractCard target, boolean upgraded) {
        this.p = p;
        this.target = target;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.upgraded = upgraded;
    }
    @Override
    public void update() {
        if(target != null) {
            target.superFlash();
            int i = 0;
            AbstractPower targetPower = null;
            for (AbstractPower power : p.powers) {
                if (power instanceof ExperimentPower && ExperimentPowerFields.attachedCard.get(power) == target) {
                    targetPower = power;
                    ExperimentPowerFields.shouldTriggerCompletions.set(power,true);
                }
            }
            if (targetPower != null) {
                Wiz.atb(new CompleteAgainAction(targetPower,upgraded));
            }
            else
                Researchers.logger.warn("Target Power is null.");
        }
        this.isDone = true;
    }
}
