package researchersmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import researchersmod.patches.occultpatchesthatliterallyexistonlyforphasetobeplayablewhileunplayable.PhasingFields;

@SpirePatch2(clz = AbstractCard.class, method = "hasEnoughEnergy")
public class PlayPhaseCardsAfterTurnEndPatch {
    @SpireInstrumentPatch
    public static ExprEditor addCheckForPhase() {
        return new ExprEditor() {
            public void edit(FieldAccess f) throws CannotCompileException {
                if (f.getClassName().equals(GameActionManager.class.getName()) && f.getFieldName().equals("turnHasEnded")) {
                    f.replace("$_ = $proceed($$) && !((Boolean)" + PhasingFields.class.getName() +".isPhasing.get(this)).booleanValue();");
                }
            }
        };
    }
}
