package researchersmod.actions.unique;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
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
        int r = 0;
        for(AbstractCard c : p.discardPile.group) {
            if(c.type == AbstractCard.CardType.STATUS) {
                r++;
                float xR = MathUtils.cos(r);
                float yR = MathUtils.sin(r);
                c.target_x = Settings.WIDTH / 2.0F + 300.0F * Settings.xScale * xR;
                c.target_y = Settings.HEIGHT / 2.0F + 300.0F * Settings.xScale * yR;
                Wiz.att(new ExhaustSpecificCardAction(c,p.discardPile));
                y++;
            }
        }
        if(upgraded) for(AbstractCard c : p.drawPile.group) {
            if(c.type == AbstractCard.CardType.STATUS) {
                Wiz.att(new ExhaustSpecificCardAction(c,p.drawPile));
                y++;
            }
        }
        AbstractCard tmp = new PlasmicEnergy();
        tmp.upgrade();
        if(y > 0) Wiz.atb(new MakeTempCardInDrawPileAction(tmp,y,true,true));
        this.isDone = true;
    }
}

