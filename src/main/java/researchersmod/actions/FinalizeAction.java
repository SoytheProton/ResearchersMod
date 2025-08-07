package researchersmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.ui.ExperimentCardManager;
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
                }
            }
            if (targetPower != null) for (int y = targetPower.amount; y > 0; y--) {
                ExperimentCardManager.complete(targetPower);
                i++;
            }
            else
                Researchers.logger.warn("Target Power is null.");
            target.superFlash();
            if(upgraded) Wiz.atb(new DrawCardAction(i));
            Wiz.atb(new GainEnergyAction(i));
        }
        this.isDone = true;
    }
}
