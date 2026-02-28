package researchersmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch2(clz = AbstractCard.class, method = "canUse")
public class PlayStatusPatch {
    /*@SpireInstrumentPatch
    public static ExprEditor Instrument() {
        return new ExprEditor() {
            @Override
            public void edit(MethodCall m) throws CannotCompileException {
                if (m.getClassName().equals(AbstractPlayer.class.getName()) && m.getMethodName().equals("hasRelic")) {
                    m.replace("$_ = $proceed($$) || " + PlayStatusPatch.class.getName() + ".Do(this);");
                }
            }
        };
    }

    @SuppressWarnings("unused")
    public static boolean Do(AbstractCard card) {
        return AbstractDungeon.player.hasPower(LetVandDocumentingPower.POWER_ID) && card.type == AbstractCard.CardType.STATUS;
    }*/
}
