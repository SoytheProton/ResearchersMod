package researchersmod.relics;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import researchersmod.util.Wiz;

import static researchersmod.Researchers.makeID;

public class HypnoticWatch extends BaseRelic {
    public static final String ID = makeID(HypnoticWatch.class.getSimpleName());
    private boolean triggeredThisTurn = false;
    public HypnoticWatch() {
        super(ID, RelicTier.BOSS, LandingSound.CLINK);
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }


    public void atTurnStart() {
        this.triggeredThisTurn = false;
    }

    public void atTurnStartPostDraw() {
        flash();
        Wiz.atb(new MakeTempCardInDrawPileAction(new Dazed(),1,true,true));
        Wiz.atb(new RelicAboveCreatureAction(Wiz.p(),this));
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        if(drawnCard.type == AbstractCard.CardType.STATUS && !triggeredThisTurn) {
            triggeredThisTurn = true;
            Wiz.atb(new RelicAboveCreatureAction(Wiz.p(),this));
            flash();
            Wiz.atb(new DiscardSpecificCardAction(drawnCard));
            Wiz.atb(new DrawCardAction(1));
            Wiz.atb(new GainEnergyAction(1));
        }
    }
}
