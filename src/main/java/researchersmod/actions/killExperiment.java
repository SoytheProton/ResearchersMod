package researchersmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import researchersmod.cards.common.ClipboardSlap;
import researchersmod.ui.ExperimentsPanel;
import researchersmod.util.Wiz;

import static researchersmod.Researchers.makeID;

public class killExperiment extends AbstractGameAction {

    private CardStrings cardStrings;

    private boolean u = false;

    private AbstractCard c;

    public killExperiment(AbstractCard card){
        this(card,false);
    }

    public killExperiment(AbstractCard card,boolean upgraded){
        this.actionType = ActionType.CARD_MANIPULATION;
        c = card;
        u = upgraded;
        cardStrings = CardCrawlGame.languagePack.getCardStrings(card.cardID);
    }

    public void update(){
        ExperimentsPanel.experimentsPile.group.remove(c);
        c.unhover();
        c.untip();
        c.stopGlowing();
        c.rawDescription = cardStrings.DESCRIPTION;
        if(u) c.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        c.applyPowers();
        c.initializeDescription();
        Wiz.adp().discardPile.addToTop(c);
        isDone = true;
    }

}
