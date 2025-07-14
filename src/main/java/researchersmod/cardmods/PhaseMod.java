package researchersmod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CardModifierPatches;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.patches.occultpatchesthatliterallyexistonlyforphasetobeplayablewhileunplayable.PhasingFields;
import researchersmod.powers.ManipulationPower;
import researchersmod.ui.ModConfig;
import researchersmod.util.KH;

import java.util.ArrayList;
import java.util.Objects;

public class PhaseMod extends AbstractCardModifier {
    public static String ID = "researchersmod:PhaseCardModifier";
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("researchersmod:Keywords");
    private boolean isFirstApplication = false;
    private boolean inherent;
    private boolean phaseNumbers = ModConfig.enablePhaseNumbers;
    public static ArrayList<AbstractCardModifier> modifiers(AbstractCard c) {
        return CardModifierPatches.CardModifierFields.cardModifiers.get(c);
    }

    public PhaseMod(boolean isInherent) {
        inherent = false; // Returning true causes issues when Saving and Quiting.
    }

    public PhaseMod() {
        inherent = false;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if(!phaseNumbers || isFirstApplication) {
        String p = LocalizedStrings.PERIOD;
        String[] cardDescription = KH.autoString(KH.hasEthereal(card,rawDescription) || KH.hasInnate(card,rawDescription) || KH.hasRetain(card,rawDescription) ? " " :
                KH.hasEtheric(card, rawDescription) ? " NL " :
                KH.hasUnplayableNL(card, rawDescription) ? "." :
                        KH.hasUnplayable(card, rawDescription) ? " " : "",
                KH.hasRetain(card,rawDescription) ? uiStrings.TEXT[3] :
                        KH.hasEthereal(card, rawDescription) ? uiStrings.TEXT[2] :
                                KH.hasEtheric(card, rawDescription) ? uiStrings.TEXT[5] :
                                        KH.hasInnate(card, rawDescription) ? uiStrings.TEXT[4] :
                                                KH.hasUnplayable(card, rawDescription) ? uiStrings.TEXT[0] + p + " NL" :
                                                        KH.hasUnplayableNL(card, rawDescription) ? uiStrings.TEXT[0] : "",
                rawDescription);
        return cardDescription[0] +
                (KH.hasUnplayableNL(card, rawDescription) ? " NL " : " ")
                + phaseString(card) + p +
                (KH.hasInnate(card, rawDescription) ||
                        (KH.hasEthereal(card, rawDescription) && !CardModifierManager.hasModifier(card, BetterEtherealMod.ID)) ||
                        KH.hasPhase(card, rawDescription) && !isFirstApplication ||
                        KH.hasRetain(card, rawDescription) ? " " :
                        (KH.hasEtheric(card,rawDescription) && !CardModifierManager.hasModifier(card, EthericMod.ID)) ? "" : " NL ")
                + cardDescription[1];
        }
        return rawDescription;
    }

    private String phaseString(AbstractCard card) {
        if(phaseNumbers && CardModifierManager.getModifiers(card, PhaseMod.ID).size() > 1) {
            return uiStrings.TEXT[1]+ " " + CardModifierManager.getModifiers(card, PhaseMod.ID).size();
        }
        return uiStrings.TEXT[1];
    }


    @Override
    public void onInitialApplication(AbstractCard card) {
        if(CardModifierManager.getModifiers(card,PhaseMod.ID).get(0) == this)
            isFirstApplication = true;
        card.tags.add(Researchers.PHASE);
        this.identifier(card);
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.tags.remove(Researchers.PHASE);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new PhaseMod();
    }

    public interface OnPhaseInterface {
        void onPhase(AbstractCard card);
    }

    public interface WhilePhaseInterface {
        float whilePhase(String valueType, float value);
    }
    public void onExhausted(AbstractCard card) {
        addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractCard tmp = card.makeSameInstanceOf();
                tmp.glowColor = Color.BLUE;
                tmp.beginGlowing();
                tmp.transparency = 0.6F;
                tmp.targetTransparency = 0.6F;
                tmp.current_y = -200.0F * Settings.scale;
                tmp.target_x = Settings.WIDTH / 2.0F;
                tmp.target_y = Settings.HEIGHT / 2.0F;
                tmp.targetAngle = 0.0F;
                tmp.purgeOnUse = true;
                PhasingFields.isPhasing.set(tmp,true);
                tmp.applyPowers();
                addToTop((new NewQueueCardAction(tmp, (AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), false, true)));
                for (AbstractPower p : AbstractDungeon.player.powers)
                    if (p instanceof OnPhaseInterface)
                        ((OnPhaseInterface) p).onPhase(card);
                Researchers.cardsPhasedThisTurn++;
                Researchers.cardsPhasedThisCombat++;
                this.isDone = true;
            }
        });
    }

    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if(PhasingFields.isPhasing.get(card) && type == DamageInfo.DamageType.NORMAL) {
            if (card instanceof WhilePhaseInterface)
                damage = ((WhilePhaseInterface) card).whilePhase("DAMAGE", damage);
            for (AbstractPower p : AbstractDungeon.player.powers)
                if (Objects.equals(p.ID, ManipulationPower.POWER_ID))
                    damage += p.amount;
        }
        return damage;
    }

    public float modifyBlock(float block, AbstractCard card) {
        if(PhasingFields.isPhasing.get(card)) {
            if (card instanceof WhilePhaseInterface)
                block = ((WhilePhaseInterface) card).whilePhase("BLOCK", block);
            for (AbstractPower p : AbstractDungeon.player.powers)
                if (Objects.equals(p.ID, ManipulationPower.POWER_ID))
                    block += p.amount;
        }
        return block;
    }


    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}