package researchersmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.powers.DistortionPower;

public class ApplyDistortionPowerToAll extends AbstractGameAction {
    public ApplyDistortionPowerToAll(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        for(AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDead && !m.isDeadOrEscaped())
                addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new DistortionPower(m, amount)));
        }
        this.isDone = true;
    }
}
