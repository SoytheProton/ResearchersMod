package researchersmod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CardModifierPatches;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import researchersmod.ui.ModConfig;
import researchersmod.util.KH;

import java.util.ArrayList;

public class BetterEtherealMod extends AbstractCardModifier {
    public static String ID = "researchersmod:EtherealCardModifier";
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("researchersmod:Keywords");
    private boolean selfRetain = false;
    public static ArrayList<AbstractCardModifier> modifiers(AbstractCard c) {
        return CardModifierPatches.CardModifierFields.cardModifiers.get(c);
    }

    @Override
    public boolean shouldApply(AbstractCard card) {
        return (!card.isEthereal || KH.hasEtheric(card,""));
    }


    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        String p = LocalizedStrings.PERIOD;
        String[] cardDescription;
        if(KH.hasEtheric(card,rawDescription)) {
            cardDescription = new String[] {rawDescription.substring(0,rawDescription.indexOf(uiStrings.TEXT[5])),rawDescription.substring(2+rawDescription.indexOf(p,rawDescription.indexOf(uiStrings.TEXT[5])))};
        }
        else cardDescription = KH.autoString(KH.hasInnate(card,rawDescription) || KH.hasUnplayableNL(card,rawDescription) ? "." :
                                 KH.hasUnplayable(card,rawDescription) ? " " : "" ,
                        KH.hasInnate(card, rawDescription) ? uiStrings.TEXT[4] :
                                KH.hasUnplayable(card, rawDescription) ? uiStrings.TEXT[0] + p + " NL" :
                                        KH.hasUnplayableNL(card, rawDescription) ? uiStrings.TEXT[0] : "",
                rawDescription);
        return cardDescription[0] +
                (KH.hasUnplayableNL(card,rawDescription) ? " NL " : " ")
                + uiStrings.TEXT[2] + p +
                (KH.hasInnate(card, rawDescription) || KH.hasPhase(card, rawDescription) || KH.hasRetain(card, rawDescription) ? " " : " NL ")
                + cardDescription[1];
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.isEthereal = true;
        if(ModConfig.etherealOverrideRetain) {
            selfRetain = card.selfRetain;
            card.selfRetain = false;
        }
        this.identifier(card);
    }

    @Override
    public void onRemove(AbstractCard card) {
        if(ModConfig.etherealOverrideRetain) card.selfRetain = selfRetain;
        card.isEthereal = false;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new BetterEtherealMod();
    }


    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
