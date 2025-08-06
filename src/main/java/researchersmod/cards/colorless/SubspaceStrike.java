package researchersmod.cards.colorless;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class SubspaceStrike extends BaseCard {
    public static final String ID = makeID(SubspaceStrike.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.ATTACK,
            CardRarity.SPECIAL,
            CardTarget.ENEMY,
            0
    );
    private int realBaseDamage;

    public SubspaceStrike() {
        super(ID, info);
        setDamage(0);
        setCustomVar("Scaling",VariableType.MAGIC,2,1);
        tags.add(CardTags.STRIKE);
        this.selfRetain = true;
        this.exhaust = true;
    }

    public void applyPowers() {
        this.realBaseDamage = this.baseDamage;
        this.baseMagicNumber = statusCount() * customVar("Scaling");
        this.baseDamage += baseMagicNumber;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }

    public void calculateCardDamage(AbstractMonster mo) {
        this.realBaseDamage = this.baseDamage;
        this.baseMagicNumber = statusCount() * customVar("Scaling");
        this.baseDamage += baseMagicNumber;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }

    private int statusCount() {
        int i = 0;
        for (AbstractCard c : Wiz.p().hand.group) {
            if(c.type == CardType.STATUS)
                i++;
        }
        return i;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.damage += this.magicNumber;
        calculateCardDamage(m);
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }
}
