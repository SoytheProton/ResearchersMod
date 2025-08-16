package researchersmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;
import researchersmod.variables.EthericVariable;

public class FerrousBlade extends BaseCard {
    public static final String ID = makeID(FerrousBlade.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            0
    );

    public FerrousBlade() {
        super(ID, info);
        setDamage(4,2);
        setPhase(true);
        EthericVariable.setBaseValue(this,2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        AbstractCard Blade = new FerrousBlade();
        Blade.costForTurn = this.costForTurn + 1;
        Blade.isCostModifiedForTurn = true;
        if(upgraded)
            Blade.upgrade();
        Wiz.atb(new MakeTempCardInHandAction(Blade));
    }
}
