package researchersmod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import researchersmod.Researchers;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.DistortionPower;
import researchersmod.util.Wiz;

import static researchersmod.Researchers.makeID;

public class    BehaviorAdjustment extends BaseRelic {
    public static final String ID = makeID(BehaviorAdjustment.class.getSimpleName());

    public BehaviorAdjustment() {
        super(ID, RelicTier.BOSS, LandingSound.MAGICAL);
        this.pool = ResearchersCharacter.Meta.CARD_COLOR;
        this.tips.add(new PowerTip(Researchers.keywords.get("Distortion").PROPER_NAME, Researchers.keywords.get("Distortion").DESCRIPTION));
    }
    @Override
    public void atBattleStart() {
        for(AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if(m.hasPower(ArtifactPower.POWER_ID)) {
                Wiz.att(new RemoveSpecificPowerAction(m, null, ArtifactPower.POWER_ID));
            }
            Wiz.atb(new ApplyPowerAction(m, null, new DistortionPower(m, 4)));
        }
        Wiz.atb(new ApplyPowerAction(Wiz.p(), Wiz.p(), new StrengthPower(Wiz.p(), -1)));
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
