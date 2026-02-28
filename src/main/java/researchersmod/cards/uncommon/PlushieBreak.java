package researchersmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import researchersmod.cards.BaseCard;
import researchersmod.cards.status.Sleepwalk;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class PlushieBreak extends BaseCard {
    public static final String ID = makeID(PlushieBreak.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public PlushieBreak() {
        super(ID, info);
        setMagic(8,2);
        setExhaust(true);
        this.cardsToPreview = new Sleepwalk();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new ApplyPowerAction(p, p, new PlatedArmorPower(p, magicNumber)));
        Wiz.atb(new MakeTempCardInHandAction(new Sleepwalk()));
    }
}
