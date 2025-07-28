package researchersmod.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.PowerTip;
import researchersmod.Researchers;
import researchersmod.cardmods.PhaseMod;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.Wiz;

import static researchersmod.Researchers.makeID;

public class DissonantTongs extends BaseRelic implements PhaseMod.WhilePhaseInterface {
    public static final String ID = makeID(DissonantTongs.class.getSimpleName());
    private final AbstractPlayer p = Wiz.p();

    public DissonantTongs() {
        super(ID, RelicTier.RARE, LandingSound.CLINK);
        this.pool = ResearchersCharacter.Meta.CARD_COLOR;
        this.tips.add(new PowerTip(Researchers.keywords.get("Phase").PROPER_NAME, Researchers.keywords.get("Phase").DESCRIPTION));
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void whilePhase(AbstractCard card) {
        card.upgrade();
    }
}
