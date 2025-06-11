package researchersmod.util;

import com.megacrit.cardcrawl.powers.AbstractPower;

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
}
