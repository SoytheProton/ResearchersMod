package researchersmod.powers;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import researchersmod.Researchers;
import researchersmod.relics.ElectromagneticEqualizer;
import researchersmod.util.Wiz;

public class InstabilityPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(InstabilityPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURNBASED = false;

    public InstabilityPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount);
        updateDescription();
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if(this.amount >= 3 && !AbstractDungeon.actionManager.turnHasEnded) {
            Wiz.att(new RemoveSpecificPowerAction(owner, owner, this));
            AbstractDungeon.actionManager.callEndTurnEarlySequence();
            CardCrawlGame.sound.play("ORB_LIGHTNING_EVOKE",0.05f);
            AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.RED.cpy(), true));
            AbstractDungeon.player.getRelic(ElectromagneticEqualizer.ID).flash();
            Wiz.atb(new RelicAboveCreatureAction(owner, AbstractDungeon.player.getRelic(ElectromagneticEqualizer.ID)));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
