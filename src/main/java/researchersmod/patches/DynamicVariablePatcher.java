package researchersmod.patches;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.RenderCustomDynamicVariable;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;

@SpirePatch2(clz= RenderCustomDynamicVariable.Inner.class, method = "subRenderDynamicVariable")
public class DynamicVariablePatcher {
    /*@SpireInsertPatch(locator = Locator.class, localvars = {"num","dv"})
    public static void Insert(BitmapFont font, GlyphLayout ___gl, int num, DynamicVariable dv) {
        if(AbstractDungeon.isPlayerInDungeon() && dv.e)

        ___gl.setText(font, variableValue);
        FontHelper.renderRotatedText(sb, font, variableValue,
                __instance.current_x, __instance.current_y,
                start_x - __instance.current_x + gl.width / 2.0f,
                i * 1.45f * -font.getCapHeight() + draw_y - __instance.current_y + -6.0f,
                __instance.angle, true, c);

        totalWidth += gl.width;
    }

    private static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(String.class,"isEmpty");
            return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[1] - 1};
        }
    }*/
}
