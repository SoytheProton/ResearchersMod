package researchersmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import researchersmod.cards.colorless.OrbitalBeacon;
import researchersmod.powers.OrbitalStrikePower;
import researchersmod.util.Wiz;

public class OrbitalStrikeAction extends AbstractGameAction {
        private boolean freeToPlayOnce;
        private AbstractCard c;
        private AbstractPlayer p;
        private int energyOnUse;
        private int damage;

        public OrbitalStrikeAction(AbstractPlayer p, AbstractCard c, boolean freeToPlayOnce, int energyOnUse, int damage) {
            this.p = p;
            this.c = c;
            this.damage = damage;
            this.freeToPlayOnce = freeToPlayOnce;
            this.duration = Settings.ACTION_DUR_XFAST;
            this.actionType = ActionType.SPECIAL;
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
                for(int i = effect; i > 0; i--) {
                    Wiz.att(new MakeTempCardInDrawPileAction(new OrbitalBeacon(),1,true,true));
                    Wiz.att(new ApplyPowerAction(p, p, new OrbitalStrikePower(p,damage)));
                }
                if (!this.freeToPlayOnce) {
                    this.p.energy.use(EnergyPanel.totalCount);
                }
            }
            this.isDone = true;
        }
}
