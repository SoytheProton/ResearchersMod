package researchersmod.powers.deprecated;
/*
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.cardmods.BetterEtherealMod;
import researchersmod.powers.BasePower;
import researchersmod.util.Wiz;
@SuppressWarnings("cannotfindsymbol")
public class EnthalpyPower extends BasePower implements NonStackablePower {
    public static final String POWER_ID = Researchers.makeID(EnthalpyPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;

    public EnthalpyPower(AbstractCreature owner, AbstractCard card) {
        super(POWER_ID, TYPE, TURNBASED, owner, 1);
        k = card;
        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        AbstractCard card = k.makeStatEquivalentCopy();
        CardModifierManager.addModifier(card,new BetterEtherealMod());
        Wiz.atb(new MakeTempCardInHandAction(card));
    }

    public void updateDescription() {
        String card = "NULL";
        if(k != null) card = k.name;
        card = card.replace(" "," #y");
        String upgrade = "";
        this.description = String.format(DESCRIPTIONS[0],card,upgrade);
    }
}
*/