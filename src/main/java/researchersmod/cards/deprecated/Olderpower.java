package researchersmod.cards.deprecated;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;

public class Olderpower extends BaseCard {
    public static final String ID = makeID(Olderpower.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private boolean skillcheck = false;
    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 8;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;
    public Olderpower() {
        super(ID, info);
        setDamage(DAMAGE,UPG_DAMAGE);
        setMagic(MAGIC,UPG_MAGIC);
    }

    public void atTurnStart() {
        super.atTurnStart();
        skillcheck = false;
    }

    public void triggerOnGlowCheck() {
        if (CardTypesPlayed(CardType.POWER) == 99 || skillcheck) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
            skillcheck = true;
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        if (skillcheck) {
            addToBot(new DrawCardAction(magicNumber));
        }
    }
}
