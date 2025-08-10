package researchersmod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.Researchers;
import researchersmod.cardmods.PhaseMod;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;

public class GravityParry extends BaseCard implements PhaseMod.WhilePhaseStatInterface {
    public static final String ID = makeID(GravityParry.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );
    public GravityParry() {
        super(ID, info);
        setDamage(5,2);
        setBlock(4,2);
        setCustomVar("blockplus",VariableType.MAGIC,4,2);
        setCustomVar("damageplus",VariableType.MAGIC,5,2);
        setPhase(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new GainBlockAction(p, p, this.block));
    }

    @Override
    public float whilePhase(String valueType, float value) {
        switch(valueType) {
            case "BLOCK":
                value += customVar("blockplus");
                break;
            case "DAMAGE":
                value += customVar("damageplus");
                break;
            default:
                Researchers.logger.warn("Unexpected value: " + valueType);
        }
        return value;
    }
}