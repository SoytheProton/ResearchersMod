package researchersmod.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.Researchers;
import researchersmod.cards.BaseCard;
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
    private static final int BLOCK = 2;
    private static final int UPG_BLOCK = 0;



    public ServerHash() {
        super(ID, info);
        setBlock(BLOCK, UPG_BLOCK);
        this.tags.add(Researchers.EXPERIMENT);
        setCustomVar("Blocc", VariableType.BLOCK, 4, 2);
        Trial = 2;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new ApplyPowerAction(p, p, new ServerHashExperiment(p, 2, this, customVar("Blocc")),1));
    }
}
