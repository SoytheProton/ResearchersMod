package researchersmod.cards.status;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

import java.util.Objects;

public class SulfurPod extends BaseCard {
    public static final String ID = makeID(SulfurPod.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.STATUS,
            CardRarity.SPECIAL,
            CardTarget.ALL_ENEMY,
            1
    );
    public SulfurPod() {
        super(ID, info);
        this.exhaust = true;
        this.selfRetain = true;
        setDamage(3, 1);
        this.isMultiDamage = true;
        setPhase(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("ATTACK_POISON"));
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.POISON));
        for(AbstractCard c : p.hand.group)
            if(Objects.equals(c.cardID, ID))
                Wiz.atb(new ExhaustSpecificCardAction(c,p.hand));
    }
}