package researchersmod.patches;
// My First SpirePatch!!!!! This will go so well. Right? Right?


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;

@SpirePatch(
        clz= UseCardAction.class,
        method=SpirePatch.CONSTRUCTOR,
        paramtypez = {
                AbstractCard.class,
                AbstractCreature.class
        }
)
public class ExperimentPurgePatch {
    public static void Postfix(UseCardAction __instance, AbstractCard __card) {
        if(__card.hasTag(Researchers.EXPERIMENT)) {
            if (__card.purgeOnUse) {
                __card.tags.add(Researchers.PURGEEXP);
                __card.purgeOnUse = false;
            } else if (__card.exhaust || __card.exhaustOnUseOnce) {
                __card.tags.add(Researchers.EXHAUSTEXP);
                __card.exhaust = false;
                __card.exhaustOnUseOnce = false;
                __instance.exhaustCard = false;
            }
        }
    }
}
