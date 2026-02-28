package researchersmod.powers.attachments;

import com.evacipated.cardcrawl.mod.stslib.patches.NeutralPowertypePatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.fields.ExperimentPowerFields;
import researchersmod.powers.BasePower;
import researchersmod.powers.interfaces.DescriptionModifier;
import researchersmod.powers.interfaces.ExperimentInterfaces;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.util.ExpDescriptionHelper;
import researchersmod.util.Wiz;

public class DamageExpAttachment extends BasePower implements InvisiblePower, NonStackablePower, ExperimentInterfaces.OnAnyCompletionInterface, ExperimentInterfaces.OnTerminateInterface, DescriptionModifier {

    public static final String POWER_ID = Researchers.makeID(DamageExpAttachment.class.getSimpleName());
    public static final PowerType TYPE = NeutralPowertypePatch.NEUTRAL;
    private static final boolean TURNBASED = false;
    public boolean upgraded;
    private int baseDamage;


    public DamageExpAttachment(AbstractCreature owner, AbstractCard attachedCard, boolean upgraded, int baseDamage) {
        super(POWER_ID, TYPE, TURNBASED, owner, 1);
        for(AbstractPower p : owner.powers) {
            if(p instanceof ExperimentPower) {
                if(ExperimentPowerFields.attachedCard.get(p) == attachedCard) {
                    ExperimentPowerFields.attachedPower.set(this,p);
                }
            }
        }
        this.upgraded = upgraded;
        this.baseDamage = baseDamage;
    }

    public boolean isStackable(AbstractPower po) {
        if(po instanceof DamageExpAttachment) {
            if(((DamageExpAttachment) po).upgraded == this.upgraded) return ExperimentPowerFields.attachedPower.get(po) == ExperimentPowerFields.attachedPower.get(this);
        }
        return false;
    }

    @Override
    public void onCompletion(AbstractPower power) {
        if(power == ExperimentPowerFields.attachedPower.get(this)) {
            for(int i = this.amount; i>0; i--) {
                AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
                if (m == null) return;
                Wiz.atb(new DamageAction(m, new DamageInfo(owner, baseDamage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }
        }
    }

    @Override
    public void onTerminate(AbstractPower power) {
        if(power == ExperimentPowerFields.attachedPower.get(this)) {
            addToBot(new RemoveSpecificPowerAction(this.owner,this.owner,this));
        }
    }

    @Override
    public String ModifyDescription(String rawDescription) {
        String description = String.format(DESCRIPTIONS[0],baseDamage);
        if(amount > 1) description += String.format(DESCRIPTIONS[1],this.amount);
        description += LocalizedStrings.PERIOD;
        return ExpDescriptionHelper.completionEffect(rawDescription,description);
    }
}
