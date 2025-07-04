package researchersmod.cards.colorless;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
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
            1
    );

    public SubspaceStrike() {
        super(ID, info);
        setDamage(2,1);
        tags.add(CardTags.STRIKE);
        this.selfRetain = true;
        this.exhaust = true;
    }

    public void applyPowers() {
        super.applyPowers();
        int i = 0;
        for(AbstractCard c : Wiz.p().hand.group) {
            if(c.type == CardType.STATUS)
                i++;
        }
        String plural = cardStrings.EXTENDED_DESCRIPTION[1];
        if(i == 1)
            plural = "";
        this.rawDescription += String.format(cardStrings.EXTENDED_DESCRIPTION[0],i,plural);
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractCard c : p.hand.group) {
            if(c.type == CardType.STATUS)
                addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
    }
}
