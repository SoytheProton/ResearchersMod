package researchersmod.powers;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.cardmods.PhaseMod;
import researchersmod.cards.common.Uplink;
import researchersmod.util.Wiz;

import java.util.Objects;

public class UplinkPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(UplinkPower.class.getSimpleName());
    public static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURNBASED = false;

    public UplinkPower(AbstractCreature owner) {
        super(POWER_ID, TYPE, TURNBASED, owner, 1);
        updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        flash();
        if(CardModifierManager.hasModifier(card, PhaseMod.ID) && !Objects.equals(card.cardID, Uplink.ID)){
            AbstractCard tmp = card.makeSameInstanceOf();
            tmp.purgeOnUse = true;
            tmp.target_x = Settings.WIDTH/2.0F;
            tmp.target_y = Settings.HEIGHT/2.0F;
            tmp.current_x = Settings.WIDTH/2.0F;
            tmp.current_y = Settings.HEIGHT/2.0F;
            ((AbstractPlayer) owner).limbo.addToBottom(tmp);
            Wiz.att(new ExhaustSpecificCardAction(tmp,((AbstractPlayer) owner).limbo));
        }
        addToBot(new ReducePowerAction(this.owner, this.owner, this,1));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
