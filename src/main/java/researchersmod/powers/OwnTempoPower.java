package researchersmod.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import researchersmod.Researchers;
import researchersmod.cards.ExperimentCard;
import researchersmod.util.Wiz;

public class OwnTempoPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(OwnTempoPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = true;

    public OwnTempoPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount);
        updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(card instanceof ExperimentCard && !card.purgeOnUse) {
            AbstractCard tmp = card.makeSameInstanceOf();
            tmp.purgeOnUse = true;
            addToTop((new NewQueueCardAction(tmp, (AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), false, true)));
            flash();
            Wiz.atb(new ReducePowerAction(owner,owner,POWER_ID,1));
        }
    }

    @Override
    public void atEndOfRound() {
        Wiz.atb(new RemoveSpecificPowerAction(this.owner,this.owner,POWER_ID));
    }

    public void updateDescription() {
        String plural = "s are";
        if(this.amount == 1)
            plural = " is";
        this.description = String.format(DESCRIPTIONS[0],this.amount,plural);
    }
}
