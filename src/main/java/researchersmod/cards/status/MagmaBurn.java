package researchersmod.cards.status;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cardmods.EthericMod;
import researchersmod.cards.BaseCard;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

import java.util.ArrayList;
import java.util.List;

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
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.dontTriggerOnUseCard) {
            addToBot((AbstractGameAction)new DamageAction((AbstractCreature) AbstractDungeon.player, new DamageInfo((AbstractCreature)AbstractDungeon.player, damage * this.magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        }
    }

    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        for(AbstractCard c : Wiz.p().hand.group) {
            if(c.type == CardType.STATUS)
                damage++;
        }
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }

    @Override
    protected List<AbstractCardModifier> getInitialModifiers() {
        ArrayList<AbstractCardModifier> retVal = new ArrayList<>();
        EthericMod etheric = new EthericMod();
        etheric.editEtheric(2);
        retVal.add(etheric);
        return retVal;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        if(!this.upgraded) {
            AbstractCardModifier mod = CardModifierManager.getModifiers(this, EthericMod.ID).get(0);
            ((EthericMod)mod).editEtheric(3);
        }
    }
}