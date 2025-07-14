package researchersmod.powers;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.cardmods.BetterEtherealMod;
import researchersmod.util.Wiz;

public class AccelechargerPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(AccelechargerPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;

    public AccelechargerPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURNBASED, owner, 1);
        updateDescription();
        this.amount2 = 1;
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        Wiz.atb(new SelectCardsAction(Wiz.p().drawPile.group,1,DESCRIPTIONS[1],false,(c -> true),(cards) -> {
            for (AbstractCard c : cards) {
                AbstractCard tmp = c.makeStatEquivalentCopy();
                CardModifierManager.addModifier(tmp, new BetterEtherealMod());
                Wiz.atb(new MakeTempCardInHandAction(tmp));
            }
        } ));
    }

    public void updateDescription() {
        String plural = "s";
        if(this.amount2 == 1) plural = "";
        this.description = String.format(DESCRIPTIONS[0],this.amount2,plural,this.amount);
    }
}
