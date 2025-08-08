package researchersmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.ExperimentCard;
import researchersmod.cards.status.ShortCircuit;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.experiments.LivewireExperiment;
import researchersmod.util.CardStats;

public class Livewire extends ExperimentCard {
    public static final String ID = makeID(Livewire.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    public Livewire() {
        super(ID, info,1);
        setMagic(2,1);
        this.cardsToPreview = new ShortCircuit();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new LivewireExperiment(p, this.trial, this)));
    }
}
