package researchersmod.cards.status;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.Researchers;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;

public class BurntDocument extends BaseCard {
    public static final String ID = makeID(BurntDocument.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            AbstractCard.CardType.STATUS,
            AbstractCard.CardRarity.COMMON,
            AbstractCard.CardTarget.SELF,
            0
    );

    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 1;

    public BurntDocument() {
        super(ID, info);
        this.exhaust = true;
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded) {
            addToBot(new DrawCardAction(magicNumber));
        }
    }
}