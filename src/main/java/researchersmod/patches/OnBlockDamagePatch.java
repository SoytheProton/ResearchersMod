package researchersmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import researchersmod.util.Wiz;

@SpirePatch2(
        clz= AbstractCreature.class,
        method = "decrementBlock"
)
public class OnBlockDamagePatch {

    public interface OnBlockDamageInterface {
        void onDamageBlocked(int blockedDamage);
    }

    @SpireInsertPatch(
            locator = Locator.class
    )
    public static void Insert(AbstractCreature __instance, int damageAmount) {
        if(__instance.isPlayer) {
            if (damageAmount < __instance.currentBlock)
                for (AbstractPower p : Wiz.p().powers) {
                    if (p instanceof OnBlockDamageInterface) ((OnBlockDamageInterface) p).onDamageBlocked(damageAmount);
                }
            else
                for (AbstractPower p : Wiz.p().powers)
                    if (p instanceof OnBlockDamageInterface)
                        ((OnBlockDamageInterface) p).onDamageBlocked(__instance.currentBlock);
        }
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(CardCrawlGame.screenShake.getClass(),"shake");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}


