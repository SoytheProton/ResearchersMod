package researchersmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import researchersmod.cards.ExperimentCard;
import researchersmod.powers.FreeExperimentsPower;
import researchersmod.util.Wiz;

public class FreeToPlayPatch {
    @SpirePatch(
            clz = AbstractCard.class,
            method = "freeToPlay"
    )
    public static class FreeToPlaySkills {
        public static boolean Postfix(boolean __result, AbstractCard __instance) {
            if (Wiz.isInCombat() && AbstractDungeon.player.hasPower(FreeExperimentsPower.POWER_ID) && __instance instanceof ExperimentCard) {
                return true;
            }
            return __result;
        }
    }
}