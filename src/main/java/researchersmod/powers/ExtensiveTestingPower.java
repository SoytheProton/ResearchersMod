package researchersmod.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.actions.common.ManualExperimentAction;
import researchersmod.cards.colorless.FieldTest;
import researchersmod.util.Wiz;

public class ExtensiveTestingPower extends BasePower implements NonStackablePower {
    public static final String POWER_ID = Researchers.makeID(ExtensiveTestingPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;
    private boolean upgraded;

    public ExtensiveTestingPower(AbstractCreature owner, boolean upgraded) {
        super(POWER_ID, TYPE, TURNBASED, owner, 1);
        updateDescription();
        this.upgraded = upgraded;
    }

    public boolean isStackable(AbstractPower po) {
        if(po instanceof ExtensiveTestingPower) {
            return ((ExtensiveTestingPower) po).upgraded == this.upgraded;
        }
        return false;
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        for(int i = this.amount; i>0; i--) {
            AbstractCard card = new FieldTest();
            if(upgraded) card.upgrade();
            card.dontTriggerOnUseCard = true;
            card.use((AbstractPlayer) owner,(AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng));
            Wiz.atb(new ManualExperimentAction(card));
        }
    }


    public void updateDescription() {
        String plural = "s";
        if(this.amount == 1) plural = "";
        String plus = "";
        if(upgraded) plus = "+";
        this.description = String.format(DESCRIPTIONS[0],this.amount,plural,plus);
    }
}
