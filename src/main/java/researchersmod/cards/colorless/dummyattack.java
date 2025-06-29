package researchersmod.cards.colorless;

import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.util.CardStats;
@NoCompendium
public class dummyattack extends BaseCard {
    public static final String ID = makeID(dummyattack.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            AbstractCard.CardType.ATTACK,
            CardRarity.SPECIAL,
            AbstractCard.CardTarget.ENEMY,
            0
    );

    private static final int DAMAGE = 0;
    private static final int UPG_DAMAGE = 1;

    public dummyattack() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }
}
