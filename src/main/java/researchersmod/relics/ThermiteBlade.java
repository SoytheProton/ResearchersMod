package researchersmod.relics;

import basemod.helpers.CardPowerTip;
import com.evacipated.cardcrawl.mod.stslib.patches.bothInterfaces.OnCreateCardInterface;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.status.MagmaBurn;
import researchersmod.character.ResearchersCharacter;
import researchersmod.ui.ModConfig;
import researchersmod.util.Wiz;

import java.util.Objects;

import static researchersmod.Researchers.makeID;

public class ThermiteBlade extends BaseRelic implements OnCreateCardInterface {
    public static final String ID = makeID(ThermiteBlade.class.getSimpleName());
    public ThermiteBlade() {
        super(ID, RelicTier.BOSS, LandingSound.HEAVY);
        this.pool = ResearchersCharacter.Meta.CARD_COLOR;
        AbstractCard tmp = new MagmaBurn(true);
        tmp.upgrade();
        tmp.baseMagicNumber = 3;
        tmp.magicNumber = 3;
        tmp.cost = 1;
        tmp.costForTurn = 1;
        tmp.isCostModifiedForTurn = false;
        tmp.isCostModified = false;
        this.tips.add(new CardPowerTip(tmp));
    }
    @Override
    public String getUpdatedDescription() {
        if(ModConfig.noConditionThermiteBlade) return DESCRIPTIONS[1];
        return DESCRIPTIONS[0];
    }

    /*@Override
    public void onCardDraw(AbstractCard drawnCard) {
        this.counter++;
        if(counter % 7 == 0) {
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
    }*/

    @Override
    public void onCreateCard(AbstractCard card) {
        if(Objects.equals(card.cardID, MagmaBurn.ID)) if(!((MagmaBurn)card).triggerThermite) return;
        if(card.type == AbstractCard.CardType.STATUS || ModConfig.noConditionThermiteBlade) {
            AbstractCard tmp = new MagmaBurn(true);
            tmp.upgrade();
            Wiz.att(new MakeTempCardInDrawPileAction(tmp, 1, true, true));
            flash();
        }
    }

    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if(Objects.equals(c.cardID, MagmaBurn.ID)) {
            flash();
            Wiz.atb(new RelicAboveCreatureAction(Wiz.p(),this));
        }
    }

    public void onVictory() {
        this.counter = -1;
    }
}
