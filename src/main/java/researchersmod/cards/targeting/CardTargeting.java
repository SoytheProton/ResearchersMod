package researchersmod.cards.targeting;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.cards.targeting.TargetingHandler;
import com.evacipated.cardcrawl.mod.stslib.patches.CustomTargeting;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import researchersmod.cards.ExperimentCard;
import researchersmod.ui.ExperimentCardManager;

public class CardTargeting extends TargetingHandler<AbstractCard> {

    @SpireEnum
    public static AbstractCard.CardTarget EXPERIMENT;
    private AbstractCard hovered = null;

    public static AbstractCard getTarget(AbstractCard card) {
        AbstractCard target = CustomTargeting.getCardTarget(card);
        if(target == null && !ExperimentCardManager.experiments.isEmpty()) return ExperimentCardManager.experiments.group.get(AbstractDungeon.cardRandomRng.random(ExperimentCardManager.experiments.group.size() - 1));
        else return target;
    }
    @Override
    public void updateHovered() {
        hovered = null;
        for (AbstractCard c : ExperimentCardManager.experiments.group) {
            c.hb.update();
            if(c.hb.hovered) {
                hovered = c;
                break;
            }
        }
    }

    @Override
    public AbstractCard getHovered() {
        return hovered;
    }

    @Override
    public void clearHovered() {
        hovered = null;
    }

    @Override
    public void renderReticle(SpriteBatch sb) {
        if (hovered != null) {
            hovered.render(sb);
        }
    }

    @Override
    public boolean hasTarget() {
        return hovered instanceof ExperimentCard;
    }
}
