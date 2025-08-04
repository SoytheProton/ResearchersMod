package researchersmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.cards.targeting.ExperimentTargeting;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.deprecated.OrganizationExpAttachment;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class Organization extends BaseCard {
    public static final String ID = makeID(Organization.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            ExperimentTargeting.EXPERIMENT,
            1
    );
    public Organization() {
        super(ID, info);
        setMagic(2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard target = ExperimentTargeting.getTarget(this);
        if(target != null) {
            target.superFlash();
            Wiz.atb(new ApplyPowerAction(p, p, new OrganizationExpAttachment(p,target,upgraded)));
        }
    }
}

