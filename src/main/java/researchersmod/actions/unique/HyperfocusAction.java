package researchersmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.cards.ExperimentCard;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.ui.ExperimentPowerFields;

public class HyperfocusAction extends AbstractGameAction {
    private final AbstractCard target;
    private final AbstractPlayer p;

    public HyperfocusAction(AbstractPlayer p, AbstractCard target) {
        this.p = p;
        this.target = target;
    }
    @Override
    public void update() {
        if(target != null) {
            target.superFlash();
            int i = 1;
            AbstractPower targetPower = null;
            for(AbstractPower power : p.powers) {
                if(power instanceof ExperimentPower && ExperimentPowerFields.attachedCard.get(power) == target) {
                    targetPower = power;
                }
            }
            for(AbstractPower power : p.powers) {
                if(power instanceof ExperimentPower && power != targetPower && !ExperimentPowerFields.instantImmunity.get(power)) {
                    i += power.amount;
                    ((ExperimentPower) power).terminateEffect();
                }
            }
            if(targetPower != null)
                targetPower.amount += i;
            else
                Researchers.logger.warn("Target Power is null.");
            ((ExperimentCard) target).trial += i;
            target.superFlash();
        }
        this.isDone = true;
    }
}
