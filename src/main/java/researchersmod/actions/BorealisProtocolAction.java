package researchersmod.actions;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import researchersmod.cardmods.PhaseMod;

public class BorealisProtocolAction extends AbstractGameAction {
    private final AbstractPlayer owner;
    private final int amount;

    public BorealisProtocolAction(AbstractPlayer owner, int amount) {
        this.owner = owner;
        this.amount = amount;
    }
    @Override
    public void update() {
        for(AbstractCard c : ((AbstractPlayer) owner).hand.group) {
            if(CardModifierManager.hasModifier(c, PhaseMod.ID) && c.type != AbstractCard.CardType.POWER) {
                for(AbstractCardModifier mod : CardModifierManager.getModifiers(c, PhaseMod.ID)) {
                    for(int i = this.amount; i>0; i--) mod.onExhausted(c);
                }
            }
        }
        this.isDone = true;
    }
}
