package researchersmod.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.cards.ExperimentCard;

public class UplinkPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(UplinkPower.class.getSimpleName());
    public static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURNBASED = false;
    private static boolean Upgraded = false;

    public UplinkPower(AbstractCreature owner, boolean isUpgraded) {
        super(POWER_ID, TYPE, TURNBASED, owner, 1);
        Upgraded = isUpgraded;
        updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        flash();
        if(card.hasTag(Researchers.PHASE)){
            AbstractCard tmp = card.makeStatEquivalentCopy();
            tmp.purgeOnUse = true;
            addToTop((new NewQueueCardAction(tmp, (AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), false, true)));
        }
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
