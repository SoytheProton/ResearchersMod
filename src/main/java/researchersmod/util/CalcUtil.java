package researchersmod.util;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class CalcUtil {
    public static float CalcBlock(float tmp) {
        for (AbstractPower p : Wiz.adp().powers) {
            tmp = p.modifyBlock(tmp);
        }

        for (AbstractPower p : Wiz.adp().powers) {
            tmp = p.modifyBlockLast(tmp);
        }
        return tmp;
    }
    public static float CalcDamage(float tmp) {
        for (AbstractRelic r : Wiz.adp().relics) {
            tmp = r.atDamageModify(tmp,null);
        }



        for (AbstractPower p : Wiz.adp().powers) {
            tmp = p.atDamageGive(tmp, DamageInfo.DamageType.NORMAL);
        }


        tmp = Wiz.adp().stance.atDamageGive(tmp, DamageInfo.DamageType.NORMAL);

        for (AbstractPower p : Wiz.adp().powers) {
            tmp = p.atDamageFinalGive(tmp, DamageInfo.DamageType.NORMAL);
        }


        if (tmp < 0.0F) {
            tmp = 0.0F;
        }
        return tmp;
    }
}
