package researchersmod.cards.uncommon;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.cardmods.PhaseMod;
import researchersmod.cards.status.BurntDocument;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.BDNextTurn;
import researchersmod.util.CardStats;

public class OpeningStatement extends BaseCard {
    public static final String ID = makeID(OpeningStatement.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.UNCOMMON,
            AbstractCard.CardTarget.SELF,
            1
    );


    private static final int BLOCK = 10;

    public OpeningStatement() {
        super(ID, info);
        setBlock(BLOCK);
        this.isInnate = true;
        this.isEthereal = true;
        this.cardsToPreview = new BurntDocument();
        this.upgPhase = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new ApplyPowerAction(p, p, new BDNextTurn(p,1),1));
    }
}
