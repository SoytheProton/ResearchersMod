package researchersmod.cards.uncommon;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.actions.HyperfocusAction;
import researchersmod.cards.BaseCard;
import researchersmod.cards.targeting.ExperimentTargeting;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class Hyperfocus extends BaseCard {
    public static final String ID = makeID(Hyperfocus.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            ExperimentTargeting.EXPERIMENT,
            1
    );
    public Hyperfocus() {
        super(ID, info);
        setCostUpgrade(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard target = ExperimentTargeting.getTarget(this);
        Wiz.atb(new HyperfocusAction(p,target));
    }
}

