package researchersmod.patches;


import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;

@SpirePatch(
        clz = MakeTempCardInDiscardAction.class,
        method = "update"
)
public class MakeTempCardInDiscardPatch {
    public static void Prefix(MakeTempCardInDiscardAction __instance, int ___numCards, AbstractCard ___c, float ___startDuration, @ByRef float[] ___duration) {
        if (___numCards > 6 && ___duration[0] == ___startDuration) {
            for (int i = 0; i < ___numCards; i++) {
                AbstractCard c = ___c.makeStatEquivalentCopy();
                AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(c));
            }
            ___duration[0] -= Gdx.graphics.getDeltaTime();
        }
    }
}
