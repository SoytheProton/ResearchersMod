package researchersmod.relics;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.interfaces.ExperimentInterfaces;
import researchersmod.util.Wiz;

import static researchersmod.Researchers.makeID;

public class Code2 extends BaseRelic implements ExperimentInterfaces.OnTerminateInterface {
    public static final String ID = makeID(Code2.class.getSimpleName());
    private boolean triggeredThisTurn = false;
    public Code2() {
        super(ID, RelicTier.BOSS, LandingSound.CLINK);
        this.pool = ResearchersCharacter.Meta.CARD_COLOR;
        this.tips.add(new PowerTip(Researchers.keywords.get("Experiment").PROPER_NAME, Researchers.keywords.get("Experiment").DESCRIPTION));
        this.tips.add(new PowerTip(Researchers.keywords.get("Terminate").PROPER_NAME, Researchers.keywords.get("Terminate").DESCRIPTION));
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void atTurnStart() {
        this.triggeredThisTurn = false;
    }

    @Override
    public void onTerminate(AbstractPower power) {
        if(!triggeredThisTurn) {
            triggeredThisTurn = true;
            Wiz.atb(new GainEnergyAction(1));
            flash();
            Wiz.atb(new RelicAboveCreatureAction(Wiz.p(),this));
        }
    }
}
