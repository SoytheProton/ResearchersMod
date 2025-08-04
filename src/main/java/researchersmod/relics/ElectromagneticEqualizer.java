package researchersmod.relics;

import basemod.helpers.CardPowerTip;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import researchersmod.Researchers;
import researchersmod.cards.curse.ElectromagneticInstability;
import researchersmod.util.Wiz;

import static researchersmod.Researchers.makeID;

public class ElectromagneticEqualizer extends BaseRelic {
    public static final String ID = makeID(ElectromagneticEqualizer.class.getSimpleName());
    public ElectromagneticEqualizer() {
        super(ID, RelicTier.BOSS, LandingSound.CLINK);
        this.tips.add(new CardPowerTip(new ElectromagneticInstability()));
        this.tips.add(new PowerTip(Researchers.keywords.get("Instability").PROPER_NAME, Researchers.keywords.get("Instability").DESCRIPTION));
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void atTurnStartPostDraw() {
        flash();
        Wiz.att(new DrawCardAction(2));
        Wiz.att(new RelicAboveCreatureAction(Wiz.p(),this));
    }

    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster++;
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new ElectromagneticInstability(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new ElectromagneticInstability(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new ElectromagneticInstability(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        UnlockTracker.markCardAsSeen("researchersmod:ElectromagneticInstability");
    }

    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster--;
    }
}
