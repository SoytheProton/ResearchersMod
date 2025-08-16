package researchersmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.DiscardAtEndOfTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import javassist.CtBehavior;
import researchersmod.fields.EthericFields;
import researchersmod.variables.EthericVariable;

import java.util.ArrayList;

@SpirePatch(
        clz= DiscardAtEndOfTurnAction.class,
        method = "update"
)
public class EthericPatch {
    @SpireInsertPatch(
            locator = EthericPatch.Locator.class,
            localvars = {"c"}
    )
    public static void Insert(DiscardAtEndOfTurnAction __instance, AbstractCard c) {
        if(EthericFields.etheric.get(c) > -1) EthericVariable.increment(c);
    }

    private static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "triggerOnEndOfPlayerTurn");
            return LineFinder.findInOrder(ctBehavior, new ArrayList<>(), finalMatcher);
        }
    }
}
