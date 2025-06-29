package researchersmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.ExperimentCard;
import researchersmod.cards.colorless.SubspaceStrike;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.experiments.ForceGuardExperiment;
import researchersmod.powers.experiments.WaveParticleExperiment;
import researchersmod.util.CardStats;

public class WaveParticle extends ExperimentCard {
    public static final String ID = makeID(WaveParticle.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    public WaveParticle() {
        super(ID, info,3,1);
        cardsToPreview = new SubspaceStrike();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new WaveParticleExperiment(p, this.Trial, this)));
    }
}
