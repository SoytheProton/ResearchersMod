package researchersmod.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.StrengthPower;
import researchersmod.Researchers;
import researchersmod.cards.status.BurntDocument;
import researchersmod.util.Wiz;

import java.util.Objects;

public class PurgeRecordsPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(PurgeRecordsPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = true;

    public PurgeRecordsPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount);
        updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.amount);
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(Objects.equals(card.cardID, BurntDocument.ID)) {
            flashWithoutSound();
            Wiz.applyToSelf(new StrengthPower(owner,this.amount));
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        flash();
        Wiz.atb(new RemoveSpecificPowerAction(owner, owner, this));
    }
}
