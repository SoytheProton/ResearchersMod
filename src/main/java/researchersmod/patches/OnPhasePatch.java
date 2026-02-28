package researchersmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;
import researchersmod.cardmods.PhaseMod;
import researchersmod.patches.occultpatchesthatliterallyexistonlyforphasetobeplayablewhileunplayable.PhasingFields;
import researchersmod.util.Wiz;

@SpirePatch(
        clz= UseCardAction.class,
        method = "update"
)
public class OnPhasePatch {
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"targetCard"}
    )
    public static void Insert(UseCardAction __instance, AbstractCard targetCard) {
        if (PhasingFields.isPhasing.get(targetCard)) {
            Wiz.p().relics.stream().filter(r -> r instanceof PhaseMod.OnPhaseInterface).forEach(r -> ((PhaseMod.OnPhaseInterface) r).onPhase(targetCard));
            Wiz.p().powers.stream().filter(r -> r instanceof PhaseMod.OnPhaseInterface).forEach(r -> ((PhaseMod.OnPhaseInterface) r).onPhase(targetCard));
            Wiz.p().hand.group.stream().filter(c -> c instanceof PhaseMod.OnPhaseInterface).forEach(c -> ((PhaseMod.OnPhaseInterface) c).onPhase(targetCard));
            Wiz.p().drawPile.group.stream().filter(c -> c instanceof PhaseMod.OnPhaseInterface).forEach(c -> ((PhaseMod.OnPhaseInterface) c).onPhase(targetCard));
            Wiz.p().discardPile.group.stream().filter(c -> c instanceof PhaseMod.OnPhaseInterface).forEach(c -> ((PhaseMod.OnPhaseInterface) c).onPhase(targetCard));
        }
    }

    private static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractDungeon.class, "getMonsters");
            return LineFinder.findAllInOrder(ctBehavior, finalMatcher);
        }
    }
}
