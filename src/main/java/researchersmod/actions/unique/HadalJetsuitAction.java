package researchersmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.powers.DistortionPower;
import researchersmod.util.Wiz;

public class HadalJetsuitAction extends AbstractGameAction {

    public HadalJetsuitAction(int amount) {
        this.actionType = ActionType.DAMAGE;
        this.amount = amount;
    }
    @Override
    public void update() {
        for(AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if(m.hasPower(DistortionPower.POWER_ID) && !m.isDead && !m.isDeadOrEscaped()) {
                Wiz.att(new LoseHPAction(m, Wiz.p(), this.amount * m.getPower(DistortionPower.POWER_ID).amount, AttackEffect.FIRE));
            }
        }
        this.isDone = true;
    }
}
