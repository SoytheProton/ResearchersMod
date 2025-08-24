package researchersmod.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.character.ResearchersCharacter;
import researchersmod.fields.ExperimentPowerFields;
import researchersmod.powers.interfaces.ExperimentInterfaces;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.util.Wiz;

import static researchersmod.Researchers.makeID;

public class MothBookmark extends BaseRelic implements ExperimentInterfaces.OnTerminateInterface {
    public static final String ID = makeID(MothBookmark.class.getSimpleName());
    public MothBookmark() {
        super(ID, RelicTier.RARE, LandingSound.CLINK);
        this.pool = ResearchersCharacter.Meta.CARD_COLOR;
        this.tips.add(new PowerTip(Researchers.keywords.get("Experiment").PROPER_NAME, Researchers.keywords.get("Experiment").DESCRIPTION));
        this.tips.add(new PowerTip(Researchers.keywords.get("Terminate").PROPER_NAME, Researchers.keywords.get("Terminate").DESCRIPTION));
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    @Override
    public void onTerminate(AbstractPower power) {
        flash();
        addToTop(new RelicAboveCreatureAction(Wiz.p(), this));
        ExperimentPowerFields.freeToTerminateOnce.set(power,true);
        ((ExperimentPower) power).terminateEffect();
    }
}
