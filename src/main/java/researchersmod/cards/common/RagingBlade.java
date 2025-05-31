package researchersmod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;

public class RagingBlade extends BaseCard {
    public static final String ID = makeID(RagingBlade.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.ATTACK,
            AbstractCard.CardRarity.COMMON,
            AbstractCard.CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 12;
    private static final int UPG_DAMAGE = 4;
    private static final int SUBDAMAGE = 6;
    private static final int UPG_SUBDAMAGE = 2;
    public RagingBlade() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(SUBDAMAGE,UPG_SUBDAMAGE);
        cardsToPreview = new Burn();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
          if (mo != m) {
              addToBot(new DamageAction(mo, new DamageInfo(p, magicNumber, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
          }
        }
        addToBot(new MakeTempCardInHandAction(new Burn(), 1));
    }
}
