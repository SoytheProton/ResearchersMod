package researchersmod.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.interfaces.ExperimentInterfaces;
import researchersmod.util.Wiz;

import static researchersmod.Researchers.makeID;

public class TripleTechChambers extends BaseRelic implements ExperimentInterfaces.OnCompletionInterface {
    public static final String ID = makeID(TripleTechChambers.class.getSimpleName());
    private final AbstractPlayer p = Wiz.p();

    public TripleTechChambers() {
        super(ID, RelicTier.RARE, LandingSound.SOLID);
        this.pool = ResearchersCharacter.Meta.CARD_COLOR;
        this.tips.add(new PowerTip(Researchers.keywords.get("Experiment").PROPER_NAME, Researchers.keywords.get("Experiment").DESCRIPTION));
        this.tips.add(new PowerTip(Researchers.keywords.get("Complete").PROPER_NAME, Researchers.keywords.get("Complete").DESCRIPTION));
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onCompletion(AbstractPower power) {
        Wiz.atb(new DamageAction((AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng),new DamageInfo(p, 3, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
    }
}
