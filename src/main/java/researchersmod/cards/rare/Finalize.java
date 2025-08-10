package researchersmod.cards.rare;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.actions.unique.FinalizeAction;
import researchersmod.cards.BaseCard;
import researchersmod.cards.targeting.ExperimentTargeting;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class Finalize extends BaseCard {
    public static final String ID = makeID(Finalize.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            ExperimentTargeting.EXPERIMENT,
            1
    );

    public Finalize() {
        super(ID, info);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard target = ExperimentTargeting.getTarget(this);
        Wiz.atb(new FinalizeAction(p,target,upgraded));
    }
}
