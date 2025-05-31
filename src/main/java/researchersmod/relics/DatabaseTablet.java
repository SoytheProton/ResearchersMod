package researchersmod.relics;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static researchersmod.Researchers.makeID;

public class DatabaseTablet extends BaseRelic {
    public static final String ID = makeID("DatabaseTablet");
    private boolean activated;

    public DatabaseTablet() {
        super(ID, RelicTier.STARTER, LandingSound.CLINK);
    }
    @Override
    public void atBattleStartPreDraw() {
        this.activated = false;
    }
    @Override
    public void atTurnStartPostDraw() {
        if(!activated) {
            activated = true;
            flash();
            addToBot(new SelectCardsInHandAction(1, DESCRIPTIONS[1], false, true, (c -> true), (cards -> {
                for (AbstractCard card : cards) {
                    addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
                }
                if (!Settings.FAST_MODE) {
                    addToTop((AbstractGameAction)new WaitAction(Settings.ACTION_DUR_MED));
                } else {
                    addToTop((AbstractGameAction)new WaitAction(Settings.ACTION_DUR_FASTER));
                }
                if (!cards.isEmpty()) {
                    addToTop(new DrawCardAction(1));
                }
            })));
        }
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
