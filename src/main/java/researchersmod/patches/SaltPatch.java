package researchersmod.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.TimeWarpPower;
import researchersmod.patches.occultpatchesthatliterallyexistonlyforphasetobeplayablewhileunplayable.PhasingFields;

@SpirePatch(
        clz= TimeWarpPower.class,
        method="onAfterUseCard",
        paramtypez = {
                AbstractCard.class,
                UseCardAction.class
        }
)
// comedy
public class SaltPatch {
    public static SpireReturn<Void> Prefix(AbstractPower __instance, AbstractCard __card, UseCardAction __action) {
        if(PhasingFields.isPhasing.get(__card)) return SpireReturn.Return();
        return SpireReturn.Continue();
    }
}
