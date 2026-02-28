package researchersmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class Overwrite extends BaseCard {
    public static final String ID = makeID(Overwrite.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    int realBaseDamage = 0;

    public Overwrite() {
        super(ID, info);
        setDamage(9,3);
        this.magicNumber = this.baseMagicNumber = 0;
        setCustomVar("scaling",3);
        // this card was originally called "Ink Blot" but I thought that was lame.
        // this card has also been entirely reworked.
        // twice.
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.LIGHTNING));
    }

    public void applyPowers() {
        this.realBaseDamage = this.baseDamage;
        this.baseMagicNumber = Wiz.p().exhaustPile.size() * customVar("scaling");
        this.baseDamage += baseMagicNumber;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }

    public void calculateCardDamage(AbstractMonster mo) {
        this.realBaseDamage = this.baseDamage;
        this.baseMagicNumber = Wiz.p().exhaustPile.size() * customVar("scaling");
        this.baseDamage += baseMagicNumber;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }
}

