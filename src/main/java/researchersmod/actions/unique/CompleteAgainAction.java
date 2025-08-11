package researchersmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.actions.common.CompletionAction;
import researchersmod.util.Wiz;

public class CompleteAgainAction extends AbstractGameAction {
    private final AbstractPower po;
    private final boolean upgraded;
    private int amount;
    public CompleteAgainAction(AbstractPower po, boolean upgraded) {
        this.po = po;
        this.upgraded = upgraded;
        this.amount = 0;
    }

    public CompleteAgainAction(AbstractPower po, boolean upgraded, int amount) {
        this.po = po;
        this.upgraded = upgraded;
        this.amount = amount;
    }

    public void update() {
        if(po.amount > 0) {
            this.amount++;
            Wiz.att(new CompleteAgainAction(po,upgraded, amount));
            Wiz.att(new CompletionAction(po,true));
        } else {
            Wiz.atb(new GainEnergyAction(amount));
            if(upgraded) Wiz.atb(new DrawCardAction(amount));
        }
        this.isDone = true;
    }
}