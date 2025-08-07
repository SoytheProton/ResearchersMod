package researchersmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import researchersmod.cards.ExperimentCard;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.ui.ExperimentPowerFields;
import researchersmod.util.Wiz;

public class NeutralyzerSerumAction extends AbstractGameAction {
        private boolean freeToPlayOnce;
        private AbstractCard c;
        private AbstractPlayer p;
        private int energyOnUse;

        public NeutralyzerSerumAction(AbstractPlayer p, AbstractCard c, boolean freeToPlayOnce, int energyOnUse) {
            this.p = p;
            this.c = c;
            this.freeToPlayOnce = freeToPlayOnce;
            this.duration = Settings.ACTION_DUR_XFAST;
            this.actionType = AbstractGameAction.ActionType.SPECIAL;
            this.energyOnUse = energyOnUse;
        }


        public void update() {
            int effect = EnergyPanel.totalCount;
            if (this.energyOnUse != -1) {
                effect = this.energyOnUse;
            }

            if (this.p.hasRelic("Chemical X")) {
                effect += 2;
                this.p.getRelic("Chemical X").flash();
            }

            if(c.upgraded)
                effect++;

            AbstractPower exp = null;

            for(AbstractPower pow : p.powers) {
                if(ExperimentPowerFields.attachedCard.get(pow) == c) {
                    exp = pow;
                }
            }

            if(effect == 0) {
                ExperimentCardManager.remExp(exp);
                Wiz.atb(new RemoveSpecificPowerAction(p,p,exp));
            }

            if (effect > 0) {
                ((ExperimentCard)c).trial += effect;
                exp.amount += effect;
                if (!this.freeToPlayOnce) {
                    this.p.energy.use(EnergyPanel.totalCount);
                }
                ((ExperimentCard)c).flash();
            }
            this.isDone = true;
        }
}
