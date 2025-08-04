package researchersmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import researchersmod.relics.interfaces.AtBlockModifyInterface;
import researchersmod.util.Wiz;

import java.util.ArrayList;

@SpirePatch(
        clz= AbstractCard.class,
        method = "applyPowersToBlock"
)
public class AtBlockModifyPatch {
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"tmp"}
    )
    public static void Insert(AbstractCard __instance, @ByRef float[] tmp) {
        for (AbstractRelic r : Wiz.p().relics) if(r instanceof AtBlockModifyInterface) tmp[0] = ((AtBlockModifyInterface) r).atBlockModify(tmp[0],__instance);
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class,"powers");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
    }
}
