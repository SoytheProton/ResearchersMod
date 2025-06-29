package researchersmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.cards.status.BurntDocument;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.RemnantResearchPower;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class RemnantResearch extends BaseCard {
    public static final String ID = makeID(RemnantResearch.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public RemnantResearch() {
        super(ID, info);
        setBlock(2,1);
        this.cardsToPreview = new BurntDocument();
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new MakeTempCardInHandAction(new BurntDocument(), 2));
        addToBot(new ApplyPowerAction(p, p, new RemnantResearchPower(p,this.block),this.block));
    }
}
