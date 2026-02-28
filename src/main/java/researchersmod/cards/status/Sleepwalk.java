package researchersmod.cards.status;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.cards.BaseCard;
import researchersmod.fields.ExperimentFields;
import researchersmod.fields.ExperimentPowerFields;
import researchersmod.powers.interfaces.ExperimentInterfaces;
import researchersmod.util.CardStats;

public class Sleepwalk extends BaseCard implements ExperimentInterfaces.OnExperimentInterface {
    public static final String ID = makeID(Sleepwalk.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.STATUS,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            -2
    );
    public Sleepwalk() {
        super(ID, info);
        this.selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void onExperiment(AbstractPower power) {
        AbstractCard c = ExperimentPowerFields.attachedCard.get(power);
        c.flash(Color.RED.cpy());
        this.flash();
        ExperimentFields.exhaustingExperiment.set(c, true);
        c.targetTransparency = 0.6F;
    }
}