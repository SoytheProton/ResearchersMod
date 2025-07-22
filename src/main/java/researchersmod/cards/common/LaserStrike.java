package researchersmod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.Researchers;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;

public class LaserStrike extends BaseCard {
    public static final String ID = makeID(LaserStrike.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private int realBaseDamage;
    public LaserStrike() {
        super(ID, info);
        setDamage(6,2);
        setCustomVar("Scaling",VariableType.MAGIC,3,1);
        tags.add(CardTags.STRIKE);
    }

    public void applyPowers() {
        this.realBaseDamage = this.baseDamage;
        this.baseMagicNumber = Researchers.expsTerminatedThisCombat * customVar("Scaling");
        this.baseDamage += baseMagicNumber;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }

    public void calculateCardDamage(AbstractMonster mo) {
        this.realBaseDamage = this.baseDamage;
        this.baseMagicNumber = Researchers.expsTerminatedThisCombat * customVar("Scaling");
        this.baseDamage += baseMagicNumber;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.damage += this.magicNumber;
        calculateCardDamage(m);
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
    }
}
