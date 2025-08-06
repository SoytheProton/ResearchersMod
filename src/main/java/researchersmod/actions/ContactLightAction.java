package researchersmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import researchersmod.cards.status.PlasmicEnergy;
import researchersmod.util.Wiz;

public class ContactLightAction extends AbstractGameAction {
    private final AbstractPlayer p;
    private boolean upgraded;

    public ContactLightAction(AbstractPlayer p, boolean upgraded) {
        this.p = p;
        this.upgraded = upgraded;
    }

    public void update() {
        int y = 0;
        for(AbstractCard c : p.drawPile.group) {
            if(c.type == AbstractCard.CardType.STATUS) {
                Wiz.att(new ExhaustSpecificCardAction(c,p.drawPile));
                y++;
            }
        }
        if(upgraded) for(AbstractCard c : p.discardPile.group) {
            if(c.type == AbstractCard.CardType.STATUS) {
                Wiz.att(new ExhaustSpecificCardAction(c,p.drawPile));
                y++;
            }
        }
        AbstractCard tmp = new PlasmicEnergy();
        tmp.upgrade();
        if(y > 0) Wiz.atb(new MakeTempCardInDiscardAction(tmp,y));
        this.isDone = true;
    }
}

