package researchersmod.cards.common;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.cards.status.BurntDocument;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class EncryptData extends BaseCard {
    public static final String ID = makeID(EncryptData.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            CardTarget.SELF,
            1
    );
    public EncryptData() {
        super(ID, info);
        setBlock(4);
        setMagic(2,1);
        // This card is pissing me off...
        // I'm the original
        //                                                      EncryptData
        cardsToPreview = new BurntDocument();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(magicNumber,cardStrings.EXTENDED_DESCRIPTION[0],true,true,(c -> true),(cards) -> {
            for (AbstractCard c : cards) {
                Wiz.att(new ExhaustSpecificCardAction(c,Wiz.p().hand,true));
                addToBot(new MakeTempCardInHandAction(new BurntDocument()));
                addToBot(new GainBlockAction(p,block));
            }
        }));
    }
}

