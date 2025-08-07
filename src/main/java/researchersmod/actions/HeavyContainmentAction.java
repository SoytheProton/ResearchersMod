package researchersmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import researchersmod.util.Wiz;

public class HeavyContainmentAction extends AbstractGameAction {
    private final AbstractPlayer p;
    private final int block;
    private final String name;

    public HeavyContainmentAction(AbstractPlayer p, int block, String name) {
        this.p = p;
        this.block = block;
        this.name = name;
    }

    @Override
    public void update() {
        int i = 0;
        for(AbstractCard c : p.hand.group) {
            Wiz.atb(new ExhaustSpecificCardAction(c,p.hand));
            i++;
        }
        Wiz.atb(new GainBlockAction(p, i * this.block));
        Wiz.applyToSelf(new NextTurnBlockPower(p, (i*this.block)/2,"Heavy Containment"));
    }
}
