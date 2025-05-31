package researchersmod.deprecated.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;

public class OldLaserStrike extends BaseCard {
    public static final String ID = makeID(OldLaserStrike.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.ATTACK,
            AbstractCard.CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 2;

    private static final int SCALING = 3;
    private static final int UPG_SCALING = 1;
    private int realBaseDamage;

    private int SkillsPlayedThisTurn() {
        int i = 0;
        if (!AbstractDungeon.actionManager.cardsPlayedThisTurn.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
                if (c.type == CardType.SKILL) {
                    i++;
                }
            }
        }
        return i;
    }

    public OldLaserStrike() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(SCALING,UPG_SCALING); // Purely used to keep track of scaling without creating another variable.
        this.baseBlock = 0;
        this.block = this.baseBlock;
    }

    public void applyPowers() {
        this.realBaseDamage = this.baseDamage;
        this.baseBlock = this.magicNumber * SkillsPlayedThisTurn();
        this.baseDamage += baseBlock;
        super.applyPowers();
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }

    public void calculateCardDamage(AbstractMonster mo) {
        this.realBaseDamage = this.baseDamage;
        this.baseBlock = this.magicNumber * SkillsPlayedThisTurn();
        this.baseDamage += baseBlock;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = (this.damage != this.baseDamage);
    }

    public void onMoveToDiscard() {
        this.baseDamage = realBaseDamage;
        super.applyPowers();
    }

    public void triggerOnEndOfPlayerTurn() {
        this.baseDamage = realBaseDamage;
        super.applyPowers();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.damage += this.magicNumber * SkillsPlayedThisTurn();
        calculateCardDamage(m);
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.LIGHTNING));
    }
}
