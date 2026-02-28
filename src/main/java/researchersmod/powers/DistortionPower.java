package researchersmod.powers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;

public class DistortionPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(DistortionPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURNBASED = false;

    public DistortionPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount);
        updateDescription();
        priority = 200;
    }

    /*@Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner) {
            flash();
            addToTop((AbstractGameAction)new DamageAction(this.owner, new DamageInfo(this.owner, (int) Math.floor(this.amount * info.output * 0.1F), DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE, true));
        }
        return damageAmount;
    }*/

    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage * (1.0F + amount * 0.1F);
        }
        return damage;
    }


    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[1],this.amount);
    }
}
