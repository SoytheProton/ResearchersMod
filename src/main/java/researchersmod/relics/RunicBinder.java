package researchersmod.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.interfaces.ExperimentInterfaces;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.util.Wiz;

import static researchersmod.Researchers.makeID;

public class RunicBinder extends BaseRelic implements ExperimentInterfaces.OnExperimentInterface {
    public static final String ID = makeID(RunicBinder.class.getSimpleName());

    public RunicBinder() {
        super(ID, RelicTier.SHOP, LandingSound.FLAT);
        this.pool = ResearchersCharacter.Meta.CARD_COLOR;
        this.tips.add(new PowerTip(Researchers.keywords.get("Experiment").PROPER_NAME, Researchers.keywords.get("Experiment").DESCRIPTION));
        this.tips.add(new PowerTip(Researchers.keywords.get("Trial").PROPER_NAME, Researchers.keywords.get("Trial").DESCRIPTION));
    }


    public void atBattleStart() {
        this.counter = 0;
    }
    @Override
    public void onExperiment(AbstractPower power) {
        if(!grayscale) {
            flash();
            Wiz.att(new RelicAboveCreatureAction(Wiz.p(),this));
            ExperimentCardManager.tickExp(power,-1);
            this.counter++;
        }
        if(this.counter == 3) {
            flash();
            this.counter = -1;
            this.grayscale = true;
        }
    }
    public void onVictory() {
        this.counter = -1;
        this.grayscale = false;
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
