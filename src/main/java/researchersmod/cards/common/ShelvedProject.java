package researchersmod.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.ExperimentCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.experiments.ShelvedProjectExperiment;
import researchersmod.util.CardStats;

public class ShelvedProject extends ExperimentCard {
    public static final String ID = makeID(ShelvedProject.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.ATTACK,
            AbstractCard.CardRarity.COMMON,
            CardTarget.ALL_ENEMY,
            2
    );

    public ShelvedProject() {
        super(ID, info,2);
        setDamage(8,3);
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ShelvedProjectExperiment(p, this.trial, this)));
    }
}
