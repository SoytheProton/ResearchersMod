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
import researchersmod.cardmods.ExperimentMod;
import researchersmod.cards.ExperimentCard;
import researchersmod.powers.BasePower;
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
        if (card.hasTag(Researchers.EXHAUSTEXP) || card.hasTag(Researchers.PURGEEXP)) {
            if(card.hasTag(Researchers.PHASE)) card.glowColor = Color.BLUE.cpy();
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
            if(p instanceof BasePower) {
                if (((BasePower)p).k == card) {
                    expPower = p;
                }
            }
        }
        for (AbstractPower p : Wiz.adp().powers) {
            if(p instanceof ExperimentInterfaces.OnExperimentInterface){
                ((ExperimentInterfaces.OnExperimentInterface) p).onExperiment(expPower);
            }
        }
        if (playSFX) {
            CardCrawlGame.sound.play("ORB_SLOT_GAIN", 0.1F);
        }
    }

    public static void remExp(AbstractCard card, AbstractPower power) {
        removeExperiment(card,power);
    }
    public static void remExp(AbstractCard card, AbstractPower power, boolean shouldExhaust) {
        removeExperiment(card, power,shouldExhaust);
    }

    public static void remExp(AbstractCard card, AbstractPower power,  boolean shouldExhaust, boolean shouldPurge) {
        removeExperiment(card, power,shouldExhaust, shouldPurge);
    }
    public static void removeExperiment(AbstractCard card, AbstractPower power) {
        removeExperiment(card,power, false);
    }
    public static void removeExperiment(AbstractCard card, AbstractPower power,boolean shouldExhaust) {
        removeExperiment(card, power, shouldExhaust,false);
    }

    public static void removeExperiment(AbstractCard card, AbstractPower power, boolean shouldExhaust,boolean shouldPurge) {
        for (AbstractPower p : Wiz.adp().powers) {
            if (p instanceof ExperimentInterfaces.OnTerminateInterface) {
                ((ExperimentInterfaces.OnTerminateInterface) p).onTerminate(power);
            }
        }
        CardModifierManager.removeModifiersById(card, ExperimentMod.ID, true);
        ((ExperimentCard) card).Trial = ((ExperimentCard) card).BaseTrial;
        card.unhover();
        card.untip();
        card.stopGlowing();
        card.flash(Color.RED.cpy());
        if (shouldPurge || card.hasTag(Researchers.PURGEEXP)) {
            experiments.removeCard(card);
            Wiz.att(new ShowCardAndPoofAction(card));
        }
        else if(experiments.group.contains(card)) {
            if (shouldExhaust || card.hasTag(Researchers.EXHAUSTEXP))
                Wiz.atb(new ExhaustSpecificCardAction(card, experiments));
            else
                Wiz.atb(new DiscardSpecificCardAction(card, experiments));
        }
        Researchers.expsTerminatedThisCombat++;
        for(AbstractCard c : Wiz.p().hand.group)
            if(c instanceof ExperimentInterfaces.OnTerminateInterface)
                ((ExperimentInterfaces.OnTerminateInterface) c).onTerminate(power);
        for(AbstractCard c : Wiz.p().discardPile.group)
            if(c instanceof ExperimentInterfaces.OnTerminateInterface)
                ((ExperimentInterfaces.OnTerminateInterface) c).onTerminate(power);
        for(AbstractCard c : Wiz.p().drawPile.group)
            if(c instanceof ExperimentInterfaces.OnTerminateInterface)
                ((ExperimentInterfaces.OnTerminateInterface) c).onTerminate(power);
        Wiz.att(new RemoveSpecificPowerAction(power.owner, power.owner, power));
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
        tickExperiment(p,amt,true);
    }

    public static void tickExperiment(AbstractPower p, int amt, boolean shouldComplete) {
        AbstractCard c = ((BasePower) p).k;
        p.amount = p.amount - amt;
        ((ExperimentCard) c).Trial = p.amount;
        if(shouldComplete && amt > 0 && p.amount >= 0) {
            for (int i = amt; i > 0; i--) {
                c.flash();
                Researchers.expsCompletedThisCombat++;
                for (AbstractPower power : Wiz.adp().powers) {
                    if (power instanceof ExperimentInterfaces.OnCompletionInterface) {
                        ((ExperimentInterfaces.OnCompletionInterface) power).onCompletion(p);
                    }
                }
            }
        }
        if (p.amount <= 0) {
            ((ExperimentPower)p).terminateEffect();
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
