package researchersmod.cards.common;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.cardmods.PhaseMod;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;

public class Confidence extends BaseCard {
    public static final String ID = makeID(Confidence.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            AbstractCard.CardTarget.SELF,
            1
    );

    private static final int BLOCK = 8;
    private static final int UPG_BLOCK = 3;

    public Confidence() {
        super(ID, info);
        setBlock(BLOCK, UPG_BLOCK);

        CardModifierManager.addModifier(this, new PhaseMod());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
    }
}
