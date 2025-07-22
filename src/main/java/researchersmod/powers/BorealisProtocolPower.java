package researchersmod.powers;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnDrawPileShufflePower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.cardmods.PhaseMod;

public class BorealisProtocolPower extends BasePower implements OnDrawPileShufflePower, NonStackablePower {
    public static final String POWER_ID = Researchers.makeID(BorealisProtocolPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;
    private final int costMax;
    public BorealisProtocolPower(AbstractCreature owner, int costMax) {
        super(POWER_ID, TYPE, TURNBASED, owner, 1);
        this.costMax = costMax;
        updateDescription();
    }

    @Override
    public void onShuffle() {
        flash();
        for(AbstractCard c : ((AbstractPlayer) owner).drawPile.group) {
            if(CardModifierManager.hasModifier(c, PhaseMod.ID) && c.cost <= costMax) {
                for(AbstractCardModifier mod : CardModifierManager.getModifiers(c, PhaseMod.ID)) {
                    for(int i = this.amount; i>0; i--) mod.onExhausted(c);
                }
            }
        }
        for(AbstractCard c : ((AbstractPlayer) owner).discardPile.group) {
            if(CardModifierManager.hasModifier(c, PhaseMod.ID) && c.cost <= costMax) {
                for(AbstractCardModifier mod : CardModifierManager.getModifiers(c, PhaseMod.ID)) {
                    for(int i = this.amount; i>0; i--) mod.onExhausted(c);
                }
            }
        }
    }

    public boolean isStackable(AbstractPower po) {
        if(po instanceof BorealisProtocolPower) {
            return ((BorealisProtocolPower)po).costMax == this.costMax;
        }
        return false;
    }
    public void updateDescription() {
        String plural = "s";
        if(this.amount == 1) plural = "";
        this.description = String.format(DESCRIPTIONS[0],this.costMax,this.amount,plural);
    }
}
