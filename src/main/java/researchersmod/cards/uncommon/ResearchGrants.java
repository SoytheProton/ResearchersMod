package researchersmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.actions.common.ApplyDistortionPowerToAll;
import researchersmod.actions.common.SetCostAction;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.patches.MoveToExhaustPilePatch;
import researchersmod.powers.DistortionPower;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class ResearchGrants extends BaseCard implements MoveToExhaustPilePatch.CardExhaustedInterface {
    public static final String ID = makeID(ResearchGrants.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            4
    );

    public ResearchGrants() {
        super(ID, info);
        setMagic(2,1);
        if(Wiz.p() != null & Wiz.isInCombat()) updateCost(-Wiz.p().exhaustPile.size());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!upgraded) {
            Wiz.atb(new ApplyPowerAction(m, p, new DistortionPower(m, magicNumber)));
        }
        else {
            addToBot(new ApplyDistortionPowerToAll(magicNumber));
        }
        Wiz.atb(new SetCostAction(this, 4, false));
    }
    @Override
    public void upgrade() {
        if(!upgraded) {
            super.upgrade();
            this.target = CardTarget.ALL_ENEMY;
        }
    }

    @Override
    public void onCardExhausted(AbstractCard card) {
        updateCost(-1);
    }
}
