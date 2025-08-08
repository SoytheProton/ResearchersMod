package researchersmod.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.ExperimentCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.experiments.ServerHashExperiment;
import researchersmod.util.CardStats;

public class ServerHash extends ExperimentCard {
    public static final String ID = makeID(ServerHash.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public ServerHash() {
        super(ID, info,2);
        setBlock(4,2);
        setCustomVar("Blocc", VariableType.BLOCK, 2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, customVar("Blocc")));
        addToBot(new ApplyPowerAction(p, p, new ServerHashExperiment(p, this.trial, this)));
    }
}
