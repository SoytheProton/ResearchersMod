package researchersmod.powers.deprecated;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import researchersmod.Researchers;
import researchersmod.powers.BasePower;
@SuppressWarnings("all")
public class OldDevelopmentPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(OldDevelopmentPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;
    private static boolean TRIGGERED = false;
    private static String CARDTYPE = "Power";

    public OldDevelopmentPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount);
        updateDescription();
    }
    @Override
    public void atStartOfTurnPostDraw() {
        TRIGGERED = false;
        updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!TRIGGERED){
            if ((AbstractDungeon.actionManager.cardsPlayedThisTurn.size() > 1) && ((AbstractCard)AbstractDungeon.actionManager.cardsPlayedThisTurn
                    .get(AbstractDungeon.actionManager.cardsPlayedThisTurn
                            .size() - 2)).type == card.type) {
                addToBot(new DrawCardAction(this.owner, this.amount));
                TRIGGERED = true;
            }
        }
        CARDTYPE = String.valueOf(card.type).substring(0,1).toUpperCase() + String.valueOf(card.type).substring(1).toLowerCase();
        updateDescription();

    }

    public void updateDescription() {
        if ((!AbstractDungeon.actionManager.cardsPlayedThisTurn.isEmpty())) {
            if (this.amount == 1) {
                if (CARDTYPE.equals("Attack")) {
                    this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + DESCRIPTIONS[3] + CARDTYPE + DESCRIPTIONS[5];
                } else {
                    this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + DESCRIPTIONS[4] + CARDTYPE + DESCRIPTIONS[5];
                }
            } else {
                if (CARDTYPE.equals("Attack")) {
                    this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2] + DESCRIPTIONS[3] + CARDTYPE + DESCRIPTIONS[5];
                } else {
                    this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2] + DESCRIPTIONS[4] + CARDTYPE + DESCRIPTIONS[5];
                }
            }
        } else {
            if (this.amount == 1) {
                this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
            } else {
                this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
            }
        }
    }
}
