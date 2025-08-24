package researchersmod.patches;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireRawPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.powers.PenNibPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import javassist.*;
import javassist.bytecode.DuplicateMemberException;
import researchersmod.cardmods.ExperimentMod;

@SuppressWarnings("unused")
public class PennibVigorPatch {
    @SpirePatch2(clz = PenNibPower.class, method = SpirePatch.CONSTRUCTOR)
    @SpirePatch2(clz = VigorPower.class, method = SpirePatch.CONSTRUCTOR)
    public static class AddAtDamageReceive {
        @SpireRawPatch
        public static void addMethod(CtBehavior ctMethodToPatch) throws NotFoundException, CannotCompileException {
            CtClass ctClass = ctMethodToPatch.getDeclaringClass();
            ClassPool classPool = ctClass.getClassPool();
            CtClass damageTypeClass = classPool.get(DamageInfo.DamageType.class.getName());
            CtClass abstractCardClass = classPool.get(AbstractCard.class.getName());
            CtClass superClass = ctClass.getSuperclass();
            CtClass[] params = new CtClass[]{CtPrimitiveType.floatType, damageTypeClass, abstractCardClass};
            CtMethod superMethod = superClass.getDeclaredMethod("atDamageGive", params);
            CtMethod newMethod = CtNewMethod.delegator(superMethod, ctClass);
            try {
                ctClass.addMethod(newMethod);
            } catch (DuplicateMemberException ignored) {
                newMethod = ctClass.getDeclaredMethod("atDamageGive", params);
            }
            newMethod.insertBefore("if("+CardModifierManager.class.getName()+".hasModifier($3,"+ExperimentMod.class.getName()+".ID)) return $1;");
        }
    }
}
