package researchersmod.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import researchersmod.Researchers;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.ManipulationPower;
import researchersmod.ui.ModConfig;
import researchersmod.util.Wiz;

import static researchersmod.Researchers.makeID;

public class BehaviorAdjustment extends BaseRelic {
    public static final String ID = makeID(BehaviorAdjustment.class.getSimpleName());
    private final AbstractPlayer p = Wiz.p();

    public BehaviorAdjustment() {
        super(ID, RelicTier.BOSS, LandingSound.MAGICAL);
        this.pool = ResearchersCharacter.Meta.CARD_COLOR;
        this.tips.add(new PowerTip(Researchers.keywords.get("Manipulation").PROPER_NAME, Researchers.keywords.get("Manipulation").DESCRIPTION));
    }
    @Override
    public void atBattleStart() {
        int i = 1;
        int m = 4;
        if(ModConfig.altBehaviorAdjustment) {
            i = AbstractDungeon.actNum;
            m--;
        }
        flash();
        addToTop((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature) p, this));
        Wiz.applyToSelf(new ManipulationPower(p,m*i));
        Wiz.applyToSelf(new DexterityPower(p,-1*i));
        Wiz.applyToSelf(new StrengthPower(p,-1*i));
    }
    @Override
    public String getUpdatedDescription() {
        if(ModConfig.altBehaviorAdjustment) return DESCRIPTIONS[1];
        return DESCRIPTIONS[0];
    }
}
