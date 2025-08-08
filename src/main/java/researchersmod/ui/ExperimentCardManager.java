package researchersmod.ui;

import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAndPoofAction;
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
import researchersmod.Researchers;
import researchersmod.actions.KillCardAction;
import researchersmod.cardmods.ExperimentMod;
import researchersmod.cards.ExperimentCard;
import researchersmod.patches.occultpatchesthatliterallyexistonlyforphasetobeplayablewhileunplayable.PhasingFields;
import researchersmod.powers.interfaces.ExperimentInterfaces;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.util.Wiz;

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
            card.initializeDescription();
            card.applyPowers();
            i++;
            if (i == 10) {
                i = 0;
                j++;
            }
        }
    }

    public static void addExp(AbstractCard card) {
        addExperiment(card);
    }

    public static void addExp(AbstractCard card, boolean playSFX) {
        addExperiment(card, playSFX);
    }


    public static void addExperiment(AbstractCard card) {
        addExp(card, true);
    }

    public static void addExperiment(AbstractCard card, boolean playSFX) {
        AbstractCard tmp = card.makeStatEquivalentCopy();
        card.targetAngle = 0f;
        if (ExperimentFields.exhaustingExperiment.get(card) || ExperimentFields.purgingExperiment.get(card)) {
            if(PhasingFields.isPhasing.get(card)) card.glowColor = Color.BLUE.cpy();
            tmp.current_x = Settings.WIDTH / 2.0F * Settings.xScale;
            tmp.current_y = Settings.HEIGHT / 2.0F;
            Wiz.att(new ShowCardAndPoofAction(tmp));
            card.targetTransparency = 0.6F;
        }
        card.stopGlowing();
        card.beginGlowing();
        if(!CardModifierManager.hasModifier(card, ExperimentMod.ID)) {
            CardModifierManager.addModifier(card, new ExperimentMod());
        }
        experiments.addToTop(card);
        AbstractPower expPower = null;
        for (AbstractPower p : Wiz.adp().powers) {
            if (ExperimentPowerFields.attachedCard.get(p) == card) {
                expPower = p;
            }
        }
        AbstractPower power = expPower;
        Wiz.p().relics.stream().filter(r-> r instanceof ExperimentInterfaces.OnExperimentInterface).forEach(r -> ((ExperimentInterfaces.OnExperimentInterface) r).onExperiment(power));
        Wiz.p().powers.stream().filter(r-> r instanceof ExperimentInterfaces.OnExperimentInterface).forEach(r -> ((ExperimentInterfaces.OnExperimentInterface) r).onExperiment(power));
        AbstractDungeon.player.hand.group.stream().filter(c -> c instanceof ExperimentInterfaces.OnExperimentInterface).forEach(c -> ((ExperimentInterfaces.OnExperimentInterface) c).onExperiment(power));
        AbstractDungeon.player.discardPile.group.stream().filter(c -> c instanceof ExperimentInterfaces.OnExperimentInterface).forEach(c -> ((ExperimentInterfaces.OnExperimentInterface) c).onExperiment(power));
        AbstractDungeon.player.drawPile.group.stream().filter(c -> c instanceof ExperimentInterfaces.OnExperimentInterface).forEach(c -> ((ExperimentInterfaces.OnExperimentInterface) c).onExperiment(power));
        if (playSFX) {
            CardCrawlGame.sound.play("ORB_SLOT_GAIN", 0.1F);
        }
    }

    public static void complete(AbstractPower power) {
        if(power.amount > 0) {
            ((ExperimentPower) power).completionEffect();
        }
    }

    public static void remExp(AbstractPower power) {
        remExp(power,false);
    }
    public static void remExp(AbstractPower power, boolean shouldExhaust) {
        remExp(power,shouldExhaust,false);
    }

    public static void remExp(AbstractPower power,  boolean shouldExhaust, boolean shouldPurge) {
        AbstractCard card = ExperimentPowerFields.attachedCard.get(power);
        removeExperiment(card,power,shouldExhaust,shouldPurge);
    }
    public static void removeExperiment(AbstractCard card, AbstractPower power) {
        removeExperiment(card,power, false);
    }
    public static void removeExperiment(AbstractCard card, AbstractPower power,boolean shouldExhaust) {
        removeExperiment(card, power, shouldExhaust,false);
    }

    public static void removeExperiment(AbstractCard card, AbstractPower power, boolean shouldExhaust, boolean shouldPurge) {
        if(ExperimentPowerFields.freeToTerminateOnce.get(power)) {
            ExperimentPowerFields.freeToTerminateOnce.set(power,false);
            return;
        }
        Wiz.p().relics.stream().filter(r-> r instanceof ExperimentInterfaces.OnTerminateInterface).forEach(r -> ((ExperimentInterfaces.OnTerminateInterface) r).onTerminate(power));
        Wiz.p().powers.stream().filter(r-> r instanceof ExperimentInterfaces.OnTerminateInterface).forEach(r -> ((ExperimentInterfaces.OnTerminateInterface) r).onTerminate(power));
        AbstractDungeon.player.hand.group.stream().filter(c -> c instanceof ExperimentInterfaces.OnTerminateInterface).forEach(c -> ((ExperimentInterfaces.OnTerminateInterface) c).onTerminate(power));
        AbstractDungeon.player.discardPile.group.stream().filter(c -> c instanceof ExperimentInterfaces.OnTerminateInterface).forEach(c -> ((ExperimentInterfaces.OnTerminateInterface) c).onTerminate(power));
        AbstractDungeon.player.drawPile.group.stream().filter(c -> c instanceof ExperimentInterfaces.OnTerminateInterface).forEach(c -> ((ExperimentInterfaces.OnTerminateInterface) c).onTerminate(power));
        Researchers.expsTerminatedThisCombat++;
        Researchers.expsTerminatedThisTurn++;
        CardModifierManager.removeModifiersById(card, ExperimentMod.ID, true);
        ((ExperimentCard) card).trial = ((ExperimentCard) card).baseTrial;
        card.unhover();
        card.untip();
        card.stopGlowing();
        card.flash(Color.RED.cpy());
        if (shouldPurge || ExperimentFields.purgingExperiment.get(card)) {
            Wiz.atb(new ShowCardAndPoofAction(card));
            Wiz.atb(new KillCardAction(card,experiments));
        }
        else if(experiments.group.contains(card)) {
            if (shouldExhaust || ExperimentFields.exhaustingExperiment.get(card))
                Wiz.atb(new ExhaustSpecificCardAction(card, experiments));
            else
                Wiz.atb(new DiscardSpecificCardAction(card, experiments));
        }
        Wiz.att(new RemoveSpecificPowerAction(power.owner, power.owner, power));
    }

    public static void tickExp(AbstractPower power) {
        tickExperiment(power);
    }

    public static void tickExp(AbstractPower power, int amt) {
        tickExperiment(power, amt);
    }

    public static void tickExp(AbstractPower power, int amt, boolean shouldComplete) {
        tickExperiment(power, amt, shouldComplete);
    }

    public static void tickExperiment(AbstractPower power) {
        tickExperiment(power,1);
    }
    public static void tickExperiment(AbstractPower power, int amt) {
        tickExperiment(power,amt,true);
    }

    public static void tickExperiment(AbstractPower power, int amt, boolean shouldComplete) {
        AbstractCard card = ExperimentPowerFields.attachedCard.get(power);
        power.amount = power.amount - amt;
        ((ExperimentCard) card).trial = power.amount;
        card.flash();
        if(amt > 0 && power.amount >= 0) {
            for (int i = amt; i > 0; i--) {
                Researchers.expsCompletedThisCombat++;
                Researchers.expsCompletedThisTurn++;
                if (shouldComplete && !(power instanceof ExperimentInterfaces.OnCompletionInterface)) {
                    Wiz.p().relics.stream().filter(r -> r instanceof ExperimentInterfaces.OnCompletionInterface).forEach(r -> ((ExperimentInterfaces.OnCompletionInterface) r).onCompletion(power));
                    Wiz.p().powers.stream().filter(r -> r instanceof ExperimentInterfaces.OnCompletionInterface).forEach(r -> ((ExperimentInterfaces.OnCompletionInterface) r).onCompletion(power));
                    AbstractDungeon.player.hand.group.stream().filter(c -> c instanceof ExperimentInterfaces.OnCompletionInterface).forEach(c -> ((ExperimentInterfaces.OnCompletionInterface) c).onCompletion(power));
                    AbstractDungeon.player.discardPile.group.stream().filter(c -> c instanceof ExperimentInterfaces.OnCompletionInterface).forEach(c -> ((ExperimentInterfaces.OnCompletionInterface) c).onCompletion(power));
                    AbstractDungeon.player.drawPile.group.stream().filter(c -> c instanceof ExperimentInterfaces.OnCompletionInterface).forEach(c -> ((ExperimentInterfaces.OnCompletionInterface) c).onCompletion(power));
                }
            }
        }
        if (power.amount <= 0) {
            ((ExperimentPower)power).terminateEffect();
        }
    }

    @SpirePatch2(clz = AbstractCard.class, method = SpirePatch.CLASS)
    public static class ProjectedCardFields {
        public static SpireField<Boolean> projectedField = new SpireField<>(() -> false);
    }

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
