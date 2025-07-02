package researchersmod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.cardmods.EtherealMod;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CardModifierPatches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.cards.ExperimentCard;
import researchersmod.patches.occultpatchesthatliterallyexistonlyforphasetobeplayablewhileunplayable.OccultFields;
import researchersmod.util.KH;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class PhaseMod extends AbstractCardModifier {
    public static String ID = "researchersmod:PhaseCardModifier";
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("researchersmod:Keywords");
    private boolean isFirstApplication = false;
    public static ArrayList<AbstractCardModifier> modifiers(AbstractCard c) {
        return CardModifierPatches.CardModifierFields.cardModifiers.get(c);
    }
    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        String p = LocalizedStrings.PERIOD;
        String[] cardDescription = KH.autoString(KH.hasUnplayableNL(card,rawDescription) ? "." :
                                        KH.hasUnplayable(card,rawDescription) ? " " : "",
                                KH.hasUnplayable(card, rawDescription) ? uiStrings.TEXT[0] + p + " NL" :
                                        KH.hasUnplayableNL(card, rawDescription) ? uiStrings.TEXT[0] : "",
                rawDescription);
        return cardDescription[0] +
                (KH.hasUnplayableNL(card,rawDescription) ? " NL " : " ")
                + uiStrings.TEXT[1] + p +
                (KH.hasInnate(card, rawDescription) ||
                        KH.hasEther(card,rawDescription) && !CardModifierManager.hasModifier(card,EtherealMod.ID) && !CardModifierManager.hasModifier(card,EthericMod.ID) ||
                        KH.hasPhase(card, rawDescription) && !isFirstApplication ||
                        KH.hasRetain(card, rawDescription) ? " " : " NL ")
                + cardDescription[1];
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        if(CardModifierManager.getModifiers(card,PhaseMod.ID).size() == 1)
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

    public interface onPhaseInterface {
        public void onPhase();
    }
    public void onExhausted(AbstractCard card) {
        addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractCard tmp = card.makeSameInstanceOf();
                tmp.current_y = -200.0F * Settings.scale;
                tmp.target_x = Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
                tmp.target_y = Settings.HEIGHT / 2.0F;
                tmp.targetAngle = 0.0F;
                if(card instanceof ExperimentCard)
                    tmp.tags.add(Researchers.PURGEEXP);
                else
                    tmp.purgeOnUse = true;
                OccultFields.isOccult.set(tmp,true);
                tmp.applyPowers();
                addToTop((new NewQueueCardAction(tmp, (AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), false, true)));
                for (AbstractPower p : AbstractDungeon.player.powers)
                    if (p instanceof onPhaseInterface)
                        ((onPhaseInterface) p).onPhase();
                this.isDone = true;
            }
        });
    }


    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}