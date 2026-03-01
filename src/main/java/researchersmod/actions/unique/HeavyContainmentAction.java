package researchersmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import researchersmod.Researchers;
import researchersmod.cards.rare.HeavyContainment;
import researchersmod.util.Wiz;

import java.util.Objects;

public class HeavyContainmentAction extends AbstractGameAction {
    private final AbstractPlayer p;
    private final boolean upgraded;
    private CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

    public HeavyContainmentAction(AbstractPlayer p, boolean upgraded) {
        this.p = p;
        this.upgraded = upgraded;
        this.duration = this.startDuration = Settings.ACTION_DUR_FASTER;
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            for (AbstractCard c : p.hand.group) {
                if(Objects.equals(c.cardID, HeavyContainment.ID) && c.costForTurn == 0) {
                    Researchers.logger.info("Infinite Detected. Terminating...");
                } else {
                    Wiz.atb(new ExhaustSpecificCardAction(c, p.hand));
                    temp.addToTop(c);
                }
            }
        tickDuration();
        return;
        }
        else if (!temp.isEmpty()) {
            for(AbstractCard c : temp.group) {
                AbstractCard tmp = c.makeStatEquivalentCopy();
                if(!upgraded) tmp.setCostForTurn(c.costForTurn - 1);
                else tmp.updateCost(-1);
                Wiz.atb(new MakeTempCardInHandAction(tmp));
            }
            temp.clear();
        }
        tickDuration();
    }
}
