package researchersmod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.cards.colorless.OrbitalBeacon;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.util.Wiz;

import java.util.Arrays;
import java.util.Objects;

public class OrbitalStrikePower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(OrbitalStrikePower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;
    private AbstractPlayer p = Wiz.p();

    public OrbitalStrikePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount);
        updateDescription();
    }

    public void onUseCard(AbstractCard card) {
        boolean beaconCheck = true;
        for (CardGroup cardGroup : Arrays.asList(p.hand, p.drawPile, p.discardPile,p.exhaustPile, ExperimentCardManager.experiments)) {
            for (AbstractCard q : cardGroup.group) {
                if(Objects.equals(q.cardID, OrbitalBeacon.ID)) {
                    beaconCheck = false;
                }
            }
        }
        if(beaconCheck) {
            flash();
            Wiz.atb(new DamageAllEnemiesAction(p,this.amount, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
            Wiz.atb(new RemoveSpecificPowerAction(owner,owner,this));
        }
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.amount);
    }
}
