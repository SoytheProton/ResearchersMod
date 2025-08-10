package researchersmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import researchersmod.ui.ExperimentCardManager;

public class ManualExperimentAction extends AbstractGameAction {
    private final AbstractCard card;
    public ManualExperimentAction(AbstractCard card) {
        this.card = card;
    }
    @Override
    public void update() {
        ExperimentCardManager.addExperiment(card);
        this.isDone = true;
    }
}
