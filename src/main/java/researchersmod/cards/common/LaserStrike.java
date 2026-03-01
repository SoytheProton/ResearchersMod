package researchersmod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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
        setDamage(7,3);
        tags.add(CardTags.STRIKE);
    }

    public void triggerOnGlowCheck() {
        if(Researchers.expsCompletedThisTurn > 0) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
        if(Researchers.expsCompletedThisTurn > 0) addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
    }
}
