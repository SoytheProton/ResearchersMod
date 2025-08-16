package researchersmod.patches;
// My First SpirePatch!!!!! This will go so well. Right? Right?


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.fields.ExperimentFields;

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
        if(ExperimentFields.playExperiment.get(__card)) {
            if (__card.purgeOnUse) {
                ExperimentFields.purgingExperiment.set(__card,true);
                __card.purgeOnUse = false;
            } else if (__card.exhaust || __card.exhaustOnUseOnce) {
                ExperimentFields.exhaustingExperiment.set(__card,true);
                __card.exhaust = false;
                __card.exhaustOnUseOnce = false;
                __instance.exhaustCard = false;
            }
        }
    }
}
