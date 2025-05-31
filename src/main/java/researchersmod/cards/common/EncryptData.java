package researchersmod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.cards.status.BurntDocument;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;

public class EncryptData extends BaseCard {
    public static final String ID = makeID(EncryptData.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            CardTarget.SELF,
            1
    );
    private static final int DAMAGE = 10;
    private static final int UPG_DAMAGE = 4;
    public EncryptData() {
        super(ID, info);
        setBlock(DAMAGE,UPG_DAMAGE);
        // yeah, the setBlock uses the DAMAGE variables. What are YOU gonna do about it!?
        cardsToPreview = new BurntDocument();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot((AbstractGameAction)new MakeTempCardInDrawPileAction(new BurntDocument(),1,true,true));
    }
}

