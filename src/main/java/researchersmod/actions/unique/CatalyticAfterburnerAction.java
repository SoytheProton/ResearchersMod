package researchersmod.actions.unique;

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
    private final AbstractPlayer p;
    private final int[] multiDamage;

    public CatalyticAfterburnerAction(AbstractPlayer p, int[] multiDamage, int amount) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.amount = amount;
        this.p = p;
        this.multiDamage = multiDamage;
    }


    public void update() {
        for (AbstractCard c : DrawCardAction.drawnCards) {
            if (c.type == AbstractCard.CardType.STATUS) {
                Wiz.atb(new DamageAllEnemiesAction(p, multiDamage, DamageInfo.DamageType.NORMAL, AttackEffect.FIRE));
            } else {
                amount--;
            }
            if(amount > 0) Wiz.att(new DrawCardAction(1,new CatalyticAfterburnerAction(p,multiDamage,amount)));
        }
        this.isDone = true;
    }
}

