package researchersmod.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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
        if(AbstractDungeon.player.hand.group.size() == BaseMod.MAX_HAND_SIZE) {
            AbstractDungeon.player.createHandIsFullDialog();
            this.isDone = true;
            return;
        }
        Wiz.atb(new SelectCardsAction(Wiz.p().drawPile.group,this.amount,String.format(DESCRIPTIONS,this.amount,plural),false,(c -> {
            for(AbstractCard card : Wiz.p().hand.group) {
                if(c == card) return false;
            }
            return true;
        }),(cards) -> {
            for (AbstractCard c : cards) {
                Wiz.atb(new AddBetterEtherealModAction(c));
                Wiz.p().drawPile.removeCard(c);
                Wiz.p().hand.addToTop(c);
                c.applyPowers();
            }
        } ));
        this.isDone = true;
    }
}
