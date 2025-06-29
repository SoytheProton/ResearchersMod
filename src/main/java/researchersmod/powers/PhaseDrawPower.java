package researchersmod.powers;

import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardModUNUSED;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.cardmods.PhaseMod;
import researchersmod.util.Wiz;

public class PhaseDrawPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(PhaseDrawPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = true;

    public PhaseDrawPower(AbstractCreature owner, int amt) {
        super(POWER_ID, TYPE, TURNBASED, owner, amt);
        updateDescription();
    }

    @Override
    public void atEndOfRound() {
        if(this.amount == 0)
            Wiz.atb(new RemoveSpecificPowerAction(this.owner,this.owner,POWER_ID));
        else
            Wiz.atb(new ReducePowerAction(this.owner,this.owner,POWER_ID,1));
    }

    public void onCardDraw(AbstractCard card) {
        flash();
        CardModifierManager.addModifier(card,new PhaseMod());
        card.flash(Color.BLUE);
    }
    public void updateDescription() {
        String plural = "s";
        if(this.amount == 1)
            plural = "";
        this.description = String.format(DESCRIPTIONS[0],this.amount,plural);
    }
}
