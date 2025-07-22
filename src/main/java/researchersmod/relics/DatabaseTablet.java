package researchersmod.relics;

import researchersmod.powers.PhaseNextCardPower;
import researchersmod.util.Wiz;

import static researchersmod.Researchers.makeID;

public class DatabaseTablet extends BaseRelic {
    public static final String ID = makeID("DatabaseTablet");
    private boolean activated;

    public DatabaseTablet() {
        super(ID, RelicTier.STARTER, LandingSound.CLINK);
    }
    @Override
    public void atBattleStartPreDraw() {
        Wiz.applyToSelf(new PhaseNextCardPower(Wiz.p(),1));
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
