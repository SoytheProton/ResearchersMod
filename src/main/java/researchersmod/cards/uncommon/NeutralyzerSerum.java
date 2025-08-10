package researchersmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.actions.unique.NeutralyzerSerumAction;
import researchersmod.cards.ExperimentCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.experiments.NeutralyzerSerumExperiment;
import researchersmod.ui.ExperimentFields;
import researchersmod.util.CardStats;

public class NeutralyzerSerum extends ExperimentCard {
    public static final String ID = makeID(NeutralyzerSerum.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            -1
    );

    public NeutralyzerSerum() {
        super(ID, info,0);
        setMagic(2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(this.energyOnUse == 0 && !upgraded) ExperimentFields.playExperiment.set(this,false);
        else ExperimentFields.playExperiment.set(this,true);
        addToBot(new ApplyPowerAction(p, p, new NeutralyzerSerumExperiment(p, this.trial, this)));
        addToBot(new NeutralyzerSerumAction(p,this,this.freeToPlayOnce,this.energyOnUse));
    }
}
