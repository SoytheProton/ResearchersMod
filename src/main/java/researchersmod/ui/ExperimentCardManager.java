package researchersmod.ui;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import researchersmod.cardmods.ExperimentMod;
import researchersmod.cards.ExperimentCard;
import researchersmod.powers.BasePower;
import researchersmod.util.ExpUtil;
import researchersmod.util.Wiz;
import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.patches.HitboxRightClick;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.red.PerfectedStrike;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.ExhaustPanel;
import com.megacrit.cardcrawl.vfx.BobEffect;
import javassist.CtBehavior;

public class ExperimentCardManager {
    public static final float Y_OFFSET = 70f * Settings.scale;
    public static final float X_OFFSET = 100f * Settings.scale;
    public static final CardGroup experiments = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    private static final BobEffect bob = new BobEffect(3.0f * Settings.scale, 3.0f);
    public static AbstractCard hovered;

    public static void render(SpriteBatch sb) {
        for (AbstractCard card : experiments.group) {
            if (card != hovered) {
                card.render(sb);
            }
        }
        if (hovered != null) {
            hovered.render(sb);
            TipHelper.renderTipForCard(hovered, sb, hovered.keywords);
        }
    }

    public static void update() {
        bob.update();
        int i = 0;
        int j = 0;
        hovered = null;
        boolean toRemove = false;
        for (AbstractCard card : experiments.group) {
            card.target_y = Wiz.adp().hb.cY + Wiz.adp().hb.height/2f + Y_OFFSET*(j+1) + bob.y;
            card.target_x = Wiz.adp().hb.cX + X_OFFSET * Math.min(9, (experiments.size()-1-10*j)) / 2f - X_OFFSET * i;
            card.targetAngle = 0f;
            card.update();
            card.hb.update();
            if (card.hb.hovered && hovered == null) {
                card.targetDrawScale = 0.75f;
                hovered = card;
            } else {
                card.targetDrawScale = 0.2f;
            }
            card.applyPowers();
            i++;
            if (i == 10) {
                i = 0;
                j++;
            }
        }
        if (toRemove) {
            if (Wiz.adp().hand.size() < BaseMod.MAX_HAND_SIZE) {
                experiments.group.remove(hovered);
                AbstractDungeon.player.hand.addToTop(hovered);
                AbstractDungeon.player.hand.refreshHandLayout();
                AbstractDungeon.player.hand.applyPowers();
            } else {
                Wiz.adp().createHandIsFullDialog();
            }
        }
    }

    public static void addExperiment(AbstractCard card) {
        addExp(card);
    }

    public static void addExperiment(AbstractCard card, boolean playSFX) {
        addExp(card, playSFX);
    }


    public static void addExp(AbstractCard card) {
        addExp(card, true);
    }

    public static void addExp(AbstractCard card, boolean playSFX) {
        card.targetAngle = 0f;
        card.beginGlowing();
        if(!CardModifierManager.hasModifier(card, ExperimentMod.ID)) {
            CardModifierManager.addModifier(card, new ExperimentMod());
        }
        experiments.addToTop(card);
        for (AbstractPower p : Wiz.adp().powers) {
            if(p instanceof ExpUtil.onExperimentInterface){
                ((ExpUtil.onExperimentInterface) p).onExperiment();
            }
        }
        if (playSFX) {
            CardCrawlGame.sound.play("ORB_SLOT_GAIN", 0.1F);
        }
    }

    public static void removeExperiment(AbstractCard card) {
        remExp(card);
    }

    public static void removeExperiment(AbstractCard card, boolean shouldExhaust) {
        remExp(card, shouldExhaust);
    }


    public static void remExp(AbstractCard card) {
        remExp(card, false);
    }

    public static void remExp(AbstractCard card, boolean shouldExhaust) {
        experiments.group.remove(card);
        for (AbstractPower p : Wiz.adp().powers) {
            if(p instanceof ExpUtil.onTerminateInterface){
                ((ExpUtil.onTerminateInterface) p).onTerminate();
            }
        }
        CardModifierManager.removeModifiersById(card, ExperimentMod.ID,true);
        ((ExperimentCard) card).Trial = ((ExperimentCard) card).BaseTrial;
        card.unhover();
        card.untip();
        card.stopGlowing();
        if (shouldExhaust) {
            Wiz.atb(new ExhaustSpecificCardAction(card,experiments));
        } else {
            Wiz.atb(new DiscardSpecificCardAction(card,experiments));
        }
    }

    public static void tickExp(AbstractPower p) {
        tickExperiment(p);
    }

    public static void tickExp(AbstractPower p, int amt) {
        tickExperiment(p, amt);
    }

    public static void tickExp(AbstractPower p, int amt, boolean shouldComplete) {
        tickExperiment(p, amt, shouldComplete);
    }

    public static void tickExperiment(AbstractPower p) {
        tickExperiment(p,1);
    }
    public static void tickExperiment(AbstractPower p, int amt) {
        tickExperiment(p,1,true);
    }

    public static void tickExperiment(AbstractPower p, int amt, boolean shouldComplete) {
        AbstractCard c = ((BasePower) p).k;
        p.amount = p.amount - amt;
        ((ExperimentCard) c).Trial = p.amount;
        if(shouldComplete && amt > 0) {
            for(int i = amt; i>0; i--) {
                for(AbstractPower power : Wiz.adp().powers){
                    if(p instanceof ExpUtil.onCompletionInterface){
                        ((ExpUtil.onCompletionInterface) power).onCompletion(power);
                    }
                }
            }
        }
    }

    @SpirePatch2(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class ProjectedCardFields {
        public static SpireField<Boolean> projectedField = new SpireField<>(() -> false);
    }

    /*@SpirePatch2(clz = AbstractPlayer.class, method = "applyStartOfTurnCards")
    public static class GrowCards {
        @SpirePrefixPatch
        public static void growCards() {
            for (AbstractCard c : cards.group) {
                ApplyGrowthAction.applyGrowth(c, 1);
            }
        }
    }*/

    @SpirePatch2(clz = OverlayMenu.class, method = "render")
    public static class RenderPanel {
        @SpireInsertPatch(locator = Locator.class)
        public static void render(OverlayMenu __instance, SpriteBatch sb) {
            ExperimentCardManager.render(sb);
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
            ExperimentCardManager.update();
        }
    }

    @SpirePatch2(clz = AbstractPlayer.class, method = "preBattlePrep")
    @SpirePatch2(clz = AbstractPlayer.class, method = "onVictory")
    public static class EmptyCards {
        @SpirePostfixPatch
        public static void yeet() {
            experiments.clear();
        }
    }

    @SpirePatch2(clz = PerfectedStrike.class, method = "countCards")
    public static class CountProjectedCardsPlz {
        @SpirePostfixPatch
        public static int getCount(int __result) {
            int projectedStrikes = (int)(ExperimentCardManager.experiments.group.stream().filter(c -> c.hasTag(AbstractCard.CardTags.STRIKE)).count());
            return __result + projectedStrikes;
        }
    }

    @SpirePatch2(clz = AbstractCard.class, method = "renderEnergy")
    public static class MakeColoredText {
        private static final Color ENERGY_COST_RESTRICTED_COLOR = new Color(1.0F, 0.3F, 0.3F, 1.0F);
        @SpireInsertPatch(locator = Locator.class, localvars = {"costColor"})
        public static void plz(AbstractCard __instance, @ByRef Color[] costColor) {
            if (AbstractDungeon.player != null && experiments.contains(__instance) && !__instance.hasEnoughEnergy()) {
                costColor[0] = ENERGY_COST_RESTRICTED_COLOR;
                costColor[0].a =  __instance.transparency;
            }
        }

        public static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                Matcher m = new Matcher.MethodCallMatcher(AbstractCard.class, "getCost");
                return LineFinder.findInOrder(ctBehavior, m);
            }
        }
    }
}
