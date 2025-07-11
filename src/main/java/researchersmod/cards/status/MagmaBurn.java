package researchersmod.cards.status;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class MagmaBurn extends BaseCard {
    public static final String ID = makeID(MagmaBurn.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.STATUS,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            -2
    );

    private int damage = 0;

    public MagmaBurn() {
        super(ID, info);
        this.exhaust = true;
        setMagic(2);
        setEtheric(2,1);
        cardsToPreview = new Burn();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.dontTriggerOnUseCard) {
                addToBot((AbstractGameAction)new DamageAction((AbstractCreature) AbstractDungeon.player, new DamageInfo((AbstractCreature)AbstractDungeon.player, this.magicNumber * damage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
                damage = 0;
        }
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
        this.rawDescription = cardStrings.DESCRIPTION + String.format(cardStrings.EXTENDED_DESCRIPTION[0],i,plural);
    }

    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        for(AbstractCard c : Wiz.p().hand.group) {
            if(c.type == CardType.STATUS)
                damage++;
        }
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }

    public void triggerOnExhaust() {
        Wiz.atb(new MakeTempCardInDiscardAction(new Burn(),1));
    }

}