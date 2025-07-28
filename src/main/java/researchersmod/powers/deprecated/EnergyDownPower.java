package researchersmod.powers.deprecated;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import researchersmod.powers.BasePower;

public class EnergyDownPower extends BasePower {
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("EnergyDownPower"); public static final String POWER_ID = "EnergyDownPower"; private boolean isFasting;

    public EnergyDownPower(AbstractCreature owner, int amount, boolean isFasting, String name) {
        super(POWER_ID,PowerType.DEBUFF,false,owner,amount);
        this.name = name;
        if (isFasting) {
            loadRegion("fasting");
        } else {
            loadRegion("energized_blue");
        }
        this.isFasting = isFasting;
        initialize();
        updateDescription();
    }

    public EnergyDownPower(AbstractCreature owner, int amount) {
        this(owner, amount, false, powerStrings.NAME);
    }


    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(powerStrings.DESCRIPTIONS[0]);
        for (int i = 0; i < this.amount; i++) {
            sb.append("[E] ");
        }
        if (powerStrings.DESCRIPTIONS[1].isEmpty()) {
            sb.append(LocalizedStrings.PERIOD);
        } else {
            sb.append(powerStrings.DESCRIPTIONS[1]);
        }
        this.description = sb.toString();
    }


    public void atStartOfTurn() {
        addToBot((AbstractGameAction)new LoseEnergyAction(this.amount));
        flash();
    }
}
