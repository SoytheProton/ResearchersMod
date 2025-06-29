package researchersmod.cards.uncommon;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cardmods.PhaseMod;
import researchersmod.cards.BaseCard;
import researchersmod.cards.ExperimentCard;
import researchersmod.cards.status.BurntDocument;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.BDNextTurn;
import researchersmod.powers.experiments.ForceGuardExperiment;
import researchersmod.powers.experiments.ServerHashExperiment;
import researchersmod.util.CardStats;

public class ForceGuard extends ExperimentCard {
    public static final String ID = makeID(ForceGuard.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public ForceGuard() {
        super(ID, info,3);
        setBlock(5,3);
        setPhase(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ForceGuardExperiment(p, this.Trial, this, this.baseBlock)));
    }
}
