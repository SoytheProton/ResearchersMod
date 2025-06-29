package researchersmod.cards.uncommon;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.cards.BaseCard;
import researchersmod.cards.ExperimentCard;
import researchersmod.cards.targeting.CardTargeting;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.BasePower;
import researchersmod.powers.experiments.OrganizationExpAttachment;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class Hyperfocus extends BaseCard {
    public static final String ID = makeID(Hyperfocus.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTargeting.EXPERIMENT,
            1
    );
    public Hyperfocus() {
        super(ID, info);
        setCostUpgrade(0);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard target = CardTargeting.getTarget(this);
        if(target != null) {
            target.superFlash();
            int i = 0;
            AbstractPower targetPower = null;
            for(AbstractPower power : p.powers) {
                if(power instanceof ExperimentPower && ((BasePower)power).k == target) {
                    targetPower = power;
                }
            }
            for(AbstractPower power : p.powers) {
                if(power instanceof ExperimentPower && power != targetPower) {
                    i += power.amount;
                    ((ExperimentPower) power).terminateEffect();
                }
            }
            targetPower.amount += i;
            ((ExperimentCard) target).Trial += i;
            target.superFlash();
        }

    }
}

