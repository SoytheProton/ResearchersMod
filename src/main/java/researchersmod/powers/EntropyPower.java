package researchersmod.powers;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.actions.common.AddPhaseModAction;
import researchersmod.cardmods.BetterEtherealMod;
import researchersmod.util.Wiz;

public class EntropyPower extends BasePower implements NonStackablePower {
    public static final String POWER_ID = Researchers.makeID(EntropyPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;
    private final boolean upgraded;

    public EntropyPower(AbstractCreature owner, boolean upg) {
        super(POWER_ID, TYPE, TURNBASED, owner, 1);
        upgraded = upg;
        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        for(int i = amount; i>0; i--) {
            AbstractCard card = AbstractDungeon.returnTrulyRandomCardInCombat().makeStatEquivalentCopy();
            if (upgraded)
                Wiz.att(new AddPhaseModAction(card));
            CardModifierManager.addModifier(card, new BetterEtherealMod());
            Wiz.atb(new MakeTempCardInHandAction(card));
        }
    }

    public boolean isStackable(AbstractPower po) {
        if(po instanceof EntropyPower) {
            return ((EntropyPower) po).upgraded == this.upgraded;
        }
        return false;
    }

    public void updateDescription() {
        String plural = "s";
        if(this.amount == 1)
            plural = "";
        String upgrade = "";
        if(upgraded)
            upgrade = DESCRIPTIONS[1];
        this.description = String.format(DESCRIPTIONS[0],this.amount,plural,upgrade);
    }
}
