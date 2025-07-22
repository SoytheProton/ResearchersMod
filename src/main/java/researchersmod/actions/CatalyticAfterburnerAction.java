package researchersmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import researchersmod.util.Wiz;

public class CatalyticAfterburnerAction
        extends AbstractGameAction {
    private int amount;
    private int total;
    private final AbstractPlayer p;
    private final int[] damage;

    public CatalyticAfterburnerAction(AbstractPlayer p,int[] damage, int amount, int total) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.amount = amount;
        this.total = total;
        this.p = p;
        this.damage = damage;
    }


    public void update() {
        for (AbstractCard c : DrawCardAction.drawnCards) {
            if (c.type == AbstractCard.CardType.STATUS) {
                total++;
            } else {
                amount--;
            }
            if(amount > 0) Wiz.att(new DrawCardAction(1,new CatalyticAfterburnerAction(p,damage,amount,total)));
            else if (total > 0) {
                for(int i = total; i>0; i--) {
                    Wiz.atb(new DamageAllEnemiesAction(p, damage, DamageInfo.DamageType.NORMAL, AttackEffect.FIRE));
                }
            }
        }
        this.isDone = true;
    }
}

