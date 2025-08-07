package researchersmod.powers.deprecated;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.powers.BasePower;

@SuppressWarnings("all")
public class ComboPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(ComboPower.class.getSimpleName());
    public static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURNBASED = false;
    private static int Block = 0;
    private static int Draw = 0;
    private static boolean Upgraded = false;

    public ComboPower(AbstractCreature owner, int B, int D, boolean isUpgraded) {
        super(POWER_ID, TYPE, TURNBASED, owner, 1);
        Block = B;
        Draw = D;
        Upgraded = isUpgraded;
        updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            flash();
            addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, Block));
            addToBot(new DrawCardAction(Draw));
        }
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    public void updateDescription() {
        if(Upgraded && !this.name.contains("+")) {
            this.name = this.name + "+";
        }
        this.description = DESCRIPTIONS[0] + this.Block + DESCRIPTIONS[1] + this.Draw + DESCRIPTIONS[2];
    }
}
