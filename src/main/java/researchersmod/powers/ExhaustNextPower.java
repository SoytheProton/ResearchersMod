package researchersmod.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;

public class ExhaustNextPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(ExhaustNextPower.class.getSimpleName());
    public static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURNBASED = false;
    private static boolean Upgraded = false;

    public ExhaustNextPower(AbstractCreature owner, boolean isUpgraded) {
        super(POWER_ID, TYPE, TURNBASED, owner, 1);
        Upgraded = isUpgraded;
        updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        this.flash();
        action.exhaustCard = true;
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    public void updateDescription() {
        if(Upgraded && !this.name.contains("+")) {
            this.name = this.name + "+";
        }
        this.description = DESCRIPTIONS[0];
    }
}
