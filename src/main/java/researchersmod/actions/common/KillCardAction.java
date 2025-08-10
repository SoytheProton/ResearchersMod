package researchersmod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class KillCardAction extends AbstractGameAction {
    private AbstractCard targetCard;
    private CardGroup group;

    public KillCardAction(AbstractCard targetCard) {
        this.targetCard = targetCard;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public KillCardAction(AbstractCard targetCard, CardGroup group) {
        this.targetCard = targetCard;
        this.group = group;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.group == null)
                this.group = AbstractDungeon.player.hand;
            if (this.group.contains(this.targetCard)) {
                if (AbstractDungeon.player.hoveredCard == targetCard)
                    AbstractDungeon.player.releaseCard();
                AbstractDungeon.actionManager.removeFromQueue(targetCard);
                targetCard.unhover();
                targetCard.untip();
                targetCard.stopGlowing();
                this.group.removeCard(targetCard);
            }
        }
        this.isDone = true;
    }
}
