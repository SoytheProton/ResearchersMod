package researchersmod.actions;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import researchersmod.Researchers;
import researchersmod.cardmods.PhaseMod;

public class AltercatedBlueprintAction extends AbstractGameAction {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("researchersmod:Blueprint");
    @Override
    public void update() {
        if(Researchers.LastPhasedCard == null) {
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, uiStrings.TEXT[0], true));
            this.isDone = true;
            return;
        }
        AbstractCard card = Researchers.LastPhasedCard.makeSameInstanceOf();
        AbstractCardModifier Phase = new PhaseMod();
        Phase.onExhausted(card);
        this.isDone = true;
    }
}
