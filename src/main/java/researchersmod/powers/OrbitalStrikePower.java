package researchersmod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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
    private final AbstractPlayer p;

    public OrbitalStrikePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount);
        updateDescription();
        p = (AbstractPlayer) owner;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        boolean beaconCheck = true;
        int i = 0;
        for (CardGroup cardGroup : Arrays.asList(p.hand, p.drawPile, p.discardPile, ExperimentCardManager.experiments)) {
            for (AbstractCard q : cardGroup.group) {
                if (Objects.equals(q.cardID, OrbitalBeacon.ID) && q != card) {
                    beaconCheck = false;
                    break;
                }
            }
        }
        if(beaconCheck) {
            flash();
            int[] tmp = new int[(AbstractDungeon.getCurrRoom()).monsters.monsters.size()];
            int y;
            for (y = 0; y < tmp.length; y++)
                tmp[y] = this.amount;
            Wiz.atb(new DamageAllEnemiesAction(p, tmp, DamageInfo.DamageType.NORMAL,AbstractGameAction.AttackEffect.FIRE));
            Wiz.atb(new RemoveSpecificPowerAction(owner,owner,this));
        }
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.amount);
    }
}
