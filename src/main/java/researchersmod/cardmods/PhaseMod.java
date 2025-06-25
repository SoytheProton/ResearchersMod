package researchersmod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CardModifierPatches;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.cards.ExperimentCard;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class PhaseMod extends AbstractCardModifier {
    public static String ID = "researchersmod:PhaseCardModifier";
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    private boolean isFirstApplication = false;
    public static ArrayList<AbstractCardModifier> modifiers(AbstractCard c) {
        return CardModifierPatches.CardModifierFields.cardModifiers.get(c);
    }

    Pattern pat = Pattern.compile("Phase\\s+([0-9]+)");
    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        int addNL = 0;
        if ((rawDescription.contains("Phase. ") || pat.matcher(rawDescription).find()) && !isFirstApplication) {
            /* int PhaseCounter = CardModifierManager.getModifiers(card, ID).size();
            String a = "a b";
            String[] cardDescriptions = a.split(" ");
            if (rawDescription.contains("Phase. ")) {
                cardDescriptions = rawDescription.split("researchersmod:Phase");
            } else {
                cardDescriptions = rawDescription.split("researchersmod:Phase\\s+([0-9]+)");
            }
            if (PhaseCounter == 1) {
                return rawDescription;
            } else {
                return String.format(uiStrings.TEXT[6], cardDescriptions[0], PhaseCounter + cardDescriptions[1]);
            } */
            addNL = 1;
        }
        if (card.isEthereal || card.retain || CardModifierManager.hasModifier(card, EthericMod.ID)) addNL = 1;
        if (rawDescription.contains("Innate. ") && card.isInnate) {
            String[] cardDescription = rawDescription.split("Innate. ", 2);
            return String.format(uiStrings.TEXT[2], cardDescription[0] + "Innate.", cardDescription[1]);
        } else if (rawDescription.contains("Unplayable. NL ") && card.cost == -2) {
            String[] cardDescription = rawDescription.split("(Unplayable\\..*?) NL ", 2);
            return String.format(uiStrings.TEXT[3 + addNL], cardDescription[0] + "Unplayable.", cardDescription[1]);
        } else if (card.cost == -2) {
            return String.format(uiStrings.TEXT[5], rawDescription);
        } else return String.format(uiStrings.TEXT[addNL], rawDescription);
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
                card.flash();
                AbstractCard tmp = card.makeSameInstanceOf();
                tmp.current_y = -200.0F * Settings.scale;
                tmp.target_x = Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
                tmp.target_y = Settings.HEIGHT / 2.0F;
                tmp.targetAngle = 0.0F;
                if(card instanceof ExperimentCard)
                    tmp.tags.add(Researchers.PURGEEXP);
                else
                    tmp.purgeOnUse = true;
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