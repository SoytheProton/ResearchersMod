package researchersmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.ExperimentCard;
import researchersmod.cards.colorless.SubspaceStrike;
import researchersmod.cards.status.BurntDocument;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.experiments.SystemErrorExperiment;
import researchersmod.powers.experiments.WaveParticleExperiment;
import researchersmod.util.CardStats;

public class SystemError extends ExperimentCard {
    public static final String ID = makeID(SystemError.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public SystemError() {
        super(ID, info,2);
        cardsToPreview = new BurntDocument();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new SystemErrorExperiment(p, this.Trial, this)));
    }
}
