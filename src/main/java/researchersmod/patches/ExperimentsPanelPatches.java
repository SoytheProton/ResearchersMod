package researchersmod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.screens.ExhaustPileViewScreen;
import com.megacrit.cardcrawl.ui.panels.ExhaustPanel;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;
import researchersmod.ui.ExperimentsPanel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

public class ExperimentsPanelPatches {
    @SpirePatch(clz = OverlayMenu.class, method = SpirePatch.CLASS)
    public static class ExperimentPanelField {
        public static SpireField<ExperimentsPanel> panel = new SpireField<>(ExperimentsPanel::new);
    }

    @SpirePatch2(clz = OverlayMenu.class, method = "update")
    public static class UpdatePanel {
        @SpireInsertPatch(locator = Locator.class)
        public static void update(OverlayMenu __instance) {
            ExperimentPanelField.panel.get(__instance).updatePositions();
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(ExhaustPanel.class, "updatePositions");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }

    @SpirePatch2(clz = OverlayMenu.class, method = "showCombatPanels")
    public static class ShowPanel {
        @SpireInsertPatch(locator = Locator.class)
        public static void show(OverlayMenu __instance) {
            ExperimentPanelField.panel.get(__instance).show();
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(ExhaustPanel.class, "show");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }

    @SpirePatch2(clz = OverlayMenu.class, method = "hideCombatPanels")
    public static class HidePanel {
        @SpireInsertPatch(locator = Locator.class)
        public static void hide(OverlayMenu __instance) {
            ExperimentPanelField.panel.get(__instance).hide();
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(ExhaustPanel.class, "hide");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }

    @SpirePatch2(clz = OverlayMenu.class, method = "render")
    public static class RenderPanel {
        @SpireInsertPatch(locator = Locator.class)
        public static void render(OverlayMenu __instance, SpriteBatch sb) {
            ExperimentPanelField.panel.get(__instance).render(sb);
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(ExhaustPanel.class, "render");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }

    @SpirePatch2(clz = AbstractPlayer.class, method = "combatUpdate")
    public static class UpdatePile {
        @SpirePostfixPatch
        public static void update(AbstractPlayer __instance) {
            ExperimentsPanel.experimentsPile.update();
        }
    }
/*
    @SpirePatch2(clz = AbstractPlayer.class, method = "draw", paramtypez = {int.class})
    public static class CheckDraw {
        @SpirePrefixPatch
        public static void draw(AbstractPlayer __instance, int ___numCards) {
            if(AbstractDungeon.overlayMenu.endTurnButton.enabled)
            {
                for(int i = 0; i < ___numCards; ++i)
                {
                    if(EvaporatePanel.evaporatePile.group.size() > 0)
                    {
                        AbstractCard evaporatedCard = EvaporatePanel.evaporatePile.group.get(AbstractDungeon.cardRandomRng.random(EvaporatePanel.evaporatePile.group.size() - 1));
                        AbstractDungeon.player.drawPile.addToTop(evaporatedCard);
                        EvaporatePanel.evaporatePile.group.remove(evaporatedCard);
                        CardTemperatureFields.reduceTemp(evaporatedCard);


                        evaporatedCard.unhover();
                        evaporatedCard.unfadeOut();
                        evaporatedCard.lighten(true);
                        evaporatedCard.fadingOut = false;

                        AbstractGameEffect e = null;
                        for (AbstractGameEffect effect : AbstractDungeon.effectList) {
                            if (effect instanceof ExhaustCardEffect) {
                                AbstractCard c = ReflectionHacks.getPrivate(effect, ExhaustCardEffect.class, "c");
                                if (c == evaporatedCard)
                                    e = effect;
                            }
                        }
                        if(e != null)
                            AbstractDungeon.effectList.remove(e);
                    }
                }
            }
        }
    }*/

    @SpirePatch2(clz = AbstractPlayer.class, method = "preBattlePrep")
    @SpirePatch2(clz = AbstractPlayer.class, method = "onVictory")
    public static class ClearPile {
        @SpirePrefixPatch
        public static void clear(AbstractPlayer __instance) {
            ExperimentsPanel.experimentsPile.clear();
        }
    }

    //Moving the card

    public static boolean shouldExperiment(AbstractCard card) {
        /*if (card.hasTag(CustomTags.VENT)) {
            return false;
        }*/
        return ExperimentField.experiment.get(card);
    }

    @SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class ExperimentField {
        public static SpireField<Boolean> experiment = new SpireField<>(() -> false);
    }

    @SpirePatch2(clz = UseCardAction.class, method = "update")
    public static class MoveToExperimentPile {
        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn<?> yeet(UseCardAction __instance, AbstractCard ___targetCard) {
            if (shouldExperiment(___targetCard)) {
                if (AbstractDungeon.player.hoveredCard == ___targetCard) {
                    AbstractDungeon.player.releaseCard();
                }
                AbstractDungeon.actionManager.removeFromQueue(___targetCard);
                ___targetCard.unhover();
                ___targetCard.untip();
                ___targetCard.stopGlowing();
                __instance.exhaustCard = false;
                ExperimentsPanel.Experiment(___targetCard);

                AbstractDungeon.player.hand.group.remove(___targetCard);
                ___targetCard.exhaustOnUseOnce = false;
                AbstractDungeon.player.onCardDrawOrDiscard();
                ExperimentField.experiment.set(___targetCard, false);
                ___targetCard.dontTriggerOnUseCard = false;
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(AbstractPlayer.class, "hasRelic");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }

    //TODO jank below

    @SpirePatch(clz = ExhaustPileViewScreen.class, method = SpirePatch.CLASS)
    public static class RenderExperimentInsteadField {
        public static SpireField<Boolean> renderExperiment = new SpireField<>(() -> false);
    }

    @SpirePatch2(clz = ExhaustPanel.class, method = "openExhaustPile")
    public static class ResetFlag {
        @SpirePrefixPatch
        public static void reset(ExhaustPanel __instance) {
            ExperimentsPanelPatches.RenderExperimentInsteadField.renderExperiment.set(AbstractDungeon.exhaustPileViewScreen, false);
        }
    }

    @SpirePatch2(clz = ExhaustPileViewScreen.class, method = "open")
    public static class GrabExperimentCards {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(FieldAccess f) throws CannotCompileException {
                    if (f.getClassName().equals(CardGroup.class.getName()) && f.getFieldName().equals("group")) {// 386
                        f.replace("$_ = researchersmod.patches.ExperimentsPanelPatches.GrabExperimentCards.getCards(this, $proceed($$));");
                    }
                }
            };
        }

        public static ArrayList<AbstractCard> getCards(ExhaustPileViewScreen screen, ArrayList<AbstractCard> cards) {
            if (RenderExperimentInsteadField.renderExperiment.get(screen)) {
                return ExperimentsPanel.experimentsPile.group;
            }
            return cards;
        }
    }

    @SpirePatch2(clz = ExhaustPileViewScreen.class, method = "render")
    public static class ChangeMessage {
        public static ExprEditor Instrument() {
            return new ExprEditor() {

                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(FontHelper.class.getName()) && m.getMethodName().equals("renderDeckViewTip")) {// 386
                        m.replace("$2 = researchersmod.patches.ExperimentsPanelPatches.ChangeMessage.change(this, $2); $_ = $proceed($$);");
                    }
                }
            };
        }

        public static String change(ExhaustPileViewScreen screen, String msg) {
            if (RenderExperimentInsteadField.renderExperiment.get(screen)) {
                return ExperimentsPanel.TEXT[2];
            }
            return msg;
        }
    }

    @SpirePatch2(clz = GetAllInBattleInstances.class, method = "get")
    public static class AddExperimentToGetAllInBattleInstances {
        @SpireInsertPatch(
                rloc = 23,
                localvars = {"cards"}
        )
        public static void get(UUID uuid, HashSet<AbstractCard> cards) {
            for (AbstractCard c : ExperimentsPanel.experimentsPile.group) {
                if (c.uuid == uuid)
                    cards.add(c);
            }
        }
    }
}
