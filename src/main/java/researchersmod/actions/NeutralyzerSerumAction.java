package researchersmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import researchersmod.cards.ExperimentCard;
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

            if (effect > 0) {
                ((ExperimentCard)c).Trial += effect;

                if (!this.freeToPlayOnce) {
                    this.p.energy.use(EnergyPanel.totalCount);
                }
                ((ExperimentCard)c).flash();
            }
            this.isDone = true;
        }
}
