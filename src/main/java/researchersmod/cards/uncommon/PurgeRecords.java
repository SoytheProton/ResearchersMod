package researchersmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.cards.status.BurntDocument;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.PurgeRecordsPower;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class PurgeRecords extends BaseCard {
    public static final String ID = makeID(PurgeRecords.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public PurgeRecords() {
        super(ID, info);
        setMagic(1);
        setEthereal(true);
        this.cardsToPreview = new BurntDocument();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(upgraded) Wiz.atb(new MakeTempCardInHandAction(new BurntDocument()));
        Wiz.applyToSelf(new PurgeRecordsPower(p,this.magicNumber));
    }
}
