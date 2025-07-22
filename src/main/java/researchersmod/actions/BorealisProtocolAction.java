package researchersmod.actions;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import researchersmod.cardmods.PhaseMod;

public class BorealisProtocolAction extends AbstractGameAction {
    private final AbstractPlayer owner;
    private final int costMax;

    public BorealisProtocolAction(AbstractPlayer owner, int costMax) {
        this.owner = owner;
        this.costMax = costMax;
    }
    @Override
    public void update() {
        this.isDone = true;
        for(AbstractCard c : ((AbstractPlayer) owner).drawPile.group) {
            if(CardModifierManager.hasModifier(c, PhaseMod.ID) && c.cost <= costMax) {
                for(AbstractCardModifier mod : CardModifierManager.getModifiers(c, PhaseMod.ID)) {
                    for(int i = this.amount; i>0; i--) mod.onExhausted(c);
                }
            }
        }
        for(AbstractCard c : ((AbstractPlayer) owner).discardPile.group) {
            if(CardModifierManager.hasModifier(c, PhaseMod.ID) && c.cost <= costMax) {
                for(AbstractCardModifier mod : CardModifierManager.getModifiers(c, PhaseMod.ID)) {
                    for(int i = this.amount; i>0; i--) mod.onExhausted(c);
                }
            }
        }
    }
}
