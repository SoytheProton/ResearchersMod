package researchersmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.cards.status.BurntDocument;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.BDNextTurn;
import researchersmod.powers.ResearchGrantsPower;
import researchersmod.util.CardStats;

public class ResearchGrants extends BaseCard {
    public static final String ID = makeID(ResearchGrants.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public ResearchGrants() {
        super(ID, info);
        setCostUpgrade(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ResearchGrantsPower(p,1),1));
    }
}
