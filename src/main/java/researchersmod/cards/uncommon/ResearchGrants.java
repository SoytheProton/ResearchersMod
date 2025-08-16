package researchersmod.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cardmods.PhaseMod;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.ManipulationPower;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class ResearchGrants extends BaseCard implements PhaseMod.OnPhaseInterface {
    public static final String ID = makeID(ResearchGrants.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            4
    );

    public ResearchGrants() {
        super(ID, info);
        setMagic(2,1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new ManipulationPower(p,this.magicNumber));
        this.cost = 4;
        this.costForTurn = 4;
        this.isCostModified = false;
    }

    @Override
    public void onPhase(AbstractCard card) {
        updateCost(-1);
    }
}
