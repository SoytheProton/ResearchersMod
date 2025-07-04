package researchersmod.actions;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import researchersmod.cardmods.BetterEtherealMod;
import researchersmod.cardmods.PhaseMod;

public class AddPhaseAndEthereal extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("researchersmod:addPhaseAndEthereal");
    public static final String[] TEXT = uiStrings.TEXT;
    private final boolean isRandom;

    public AddPhaseAndEthereal(int amount, boolean isRandom){
        this.actionType = ActionType.CARD_MANIPULATION;
        this.amount = amount;
        this.isRandom = isRandom;

    }
    public void update() {
        if (this.isRandom) {
            for (int i = 0; i < this.amount; i++) {
                AbstractCard c = AbstractDungeon.player.hand.getRandomCard(AbstractDungeon.cardRandomRng);
                CardModifierManager.addModifier(c, new BetterEtherealMod());
                CardModifierManager.addModifier(c, new PhaseMod());
            }
        }
        else {
            for (AbstractCard c : AbstractDungeon.player.hand.group){
                CardModifierManager.addModifier(c, new BetterEtherealMod());
                CardModifierManager.addModifier(c, new PhaseMod());
            }
        }
        this.isDone = true;
    }
}
