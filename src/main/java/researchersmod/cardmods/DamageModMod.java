package researchersmod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.Researchers;

public class DamageModMod extends AbstractCardModifier {
    public static String ID = Researchers.makeID(DamageModMod.class.getSimpleName());
    private float damageMod;
    private boolean isMult;
    public DamageModMod() {
        this(0,false);
    }

    public DamageModMod(float damageMod, boolean isMult) {
        this.damageMod = damageMod;
        this.isMult = isMult;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DamageModMod();
    }

    private float calcFloat(float f) {
        float tmp = f;
        if(isMult) tmp  = f * damageMod;
        else tmp += damageMod;
        return tmp;
    }

    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if(type == DamageInfo.DamageType.NORMAL) return calcFloat(damage);
        return damage;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}