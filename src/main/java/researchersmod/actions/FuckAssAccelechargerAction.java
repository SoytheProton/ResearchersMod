package researchersmod.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import researchersmod.util.Wiz;

public class FuckAssAccelechargerAction extends AbstractGameAction {

    private final String DESCRIPTIONS;
    private final String plural;

    public FuckAssAccelechargerAction(String DESCRIPTION, String plural, int amount) {
        this.DESCRIPTIONS = DESCRIPTION;
        this.plural = plural;
        this.amount = amount;
    }
    @Override
    public void update() {
        Wiz.atb(new SelectCardsAction(Wiz.p().drawPile.group,this.amount,String.format(DESCRIPTIONS,this.amount,plural),false,(c -> {
            for(AbstractCard card : Wiz.p().hand.group) {
                if(c == card) return false;
            }
            return true;
        }),(cards) -> {
            for (AbstractCard c : cards) {
                Wiz.atb(new AddBetterEtherealMod(c));
                Wiz.p().drawPile.removeCard(c);
                Wiz.p().hand.addToTop(c);
            }
        } ));
        this.isDone = true;
    }
}
