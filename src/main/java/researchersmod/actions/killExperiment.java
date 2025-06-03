package researchersmod.actions;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import researchersmod.cardmods.ExperimentMod;
import researchersmod.cards.common.ClipboardSlap;
import researchersmod.ui.ExperimentsPanel;
import researchersmod.util.Wiz;

import static researchersmod.Researchers.makeID;

public class killExperiment extends AbstractGameAction {

    private AbstractCard c;

    public killExperiment(AbstractCard card){
        this.actionType = ActionType.CARD_MANIPULATION;
        c = card;
    }

    public void update(){
        ExperimentsPanel.experimentsPile.group.remove(c);
        CardModifierManager.removeModifiersById(c, "researchersmod:ExperimentModifier",true);
        c.unhover();
        c.untip();
        c.stopGlowing();
        Wiz.adp().discardPile.addToTop(c);
        isDone = true;
    }

}
