package researchersmod.relics;

import basemod.helpers.CardPowerTip;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import researchersmod.cards.status.MagmaBurn;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.Wiz;

import static researchersmod.Researchers.makeID;

public class ThermiteBlade extends BaseRelic {
    public static final String ID = makeID(ThermiteBlade.class.getSimpleName());
    public ThermiteBlade() {
        super(ID, RelicTier.BOSS, LandingSound.HEAVY);
        this.pool = ResearchersCharacter.Meta.CARD_COLOR;
        AbstractCard tmp = new MagmaBurn();
        tmp.upgrade();
        this.tips.add(new CardPowerTip(tmp));
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void atBattleStart() {
        this.counter = 0;
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        this.counter++;
        if(counter % 12 == 0) {
            flash();
            counter = 0;
            AbstractCard tmp = new MagmaBurn();
            tmp.upgrade();
            Wiz.att(new MakeTempCardInDrawPileAction(tmp,1,true,true));
            Wiz.att(new RelicAboveCreatureAction(Wiz.p(),this));
        }
        if(drawnCard.type == AbstractCard.CardType.STATUS) {
            flash();
            Wiz.atb(new RelicAboveCreatureAction(Wiz.p(),this));
            Wiz.atb(new DrawCardAction(1));
            Wiz.atb(new GainBlockAction(Wiz.p(),1));
        }
    }

    public void onVictory() {
        this.counter = -1;
    }
}
