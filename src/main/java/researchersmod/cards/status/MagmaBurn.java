package researchersmod.cards.status;

import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.relics.ThermiteBlade;
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
    private int statusCount = 0;
    public boolean triggerThermite = true;

    private void setThermite() {
        this.baseMagicNumber = 3;
        this.magicNumber = 3;
        this.cost = 1;
        this.baseCost = 1;
        if(!isCostModifiedForTurn) {
            this.costForTurn = 1;
            this.isCostModified = false;
        }
    }

    public MagmaBurn() {
        super(ID, info);
        setMagic(2);
        if(AbstractDungeon.player != null) if(AbstractDungeon.player.hasRelic(ThermiteBlade.ID)) setThermite();
        ExhaustiveVariable.setBaseValue(this,2);
    }

    public MagmaBurn(boolean madeByThermite) {
        this();
        triggerThermite = !madeByThermite;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.dontTriggerOnUseCard) {
                addToBot((AbstractGameAction)new DamageAction((AbstractCreature) AbstractDungeon.player, new DamageInfo((AbstractCreature)AbstractDungeon.player, this.magicNumber * statusCount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
            statusCount = 0;
        } else {
            if(AbstractDungeon.player.hasRelic("researchersmod:ThermiteBlade")) {
                for(AbstractCard c : Wiz.p().hand.group) {
                    if(c.type == CardType.STATUS)
                        statusCount++;
                }
                addToBot((AbstractGameAction)new DamageAllEnemiesAction(AbstractDungeon.player, this.magicNumber * statusCount, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
                statusCount = 0;
            }
        }
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!AbstractDungeon.player.hasRelic("Medical Kit") && !AbstractDungeon.player.hasRelic(ThermiteBlade.ID))
            return false;
        return cardPlayable(m) && hasEnoughEnergy();
    }

    public void applyPowers() {
        super.applyPowers();
        int i = 0;
        for(AbstractCard c : Wiz.p().hand.group) {
            if(c.type == CardType.STATUS)
                i++;
        }
        if(AbstractDungeon.player.hasRelic(ThermiteBlade.ID)) {
            setThermite();
        }
        String plural = cardStrings.EXTENDED_DESCRIPTION[1];
        if(i == 1)
            plural = "";
        this.rawDescription = cardStrings.DESCRIPTION + String.format(cardStrings.EXTENDED_DESCRIPTION[0],i,plural);
        initializeDescription();
    }

    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        for(AbstractCard c : Wiz.p().hand.group) {
            if(c.type == CardType.STATUS)
                statusCount++;
        }
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    public void upgrade() {
        if(!upgraded) {
            super.upgrade();
            ExhaustiveVariable.upgrade(this,1);
        }
    }

    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard retVal = super.makeStatEquivalentCopy();
        ((MagmaBurn) retVal).triggerThermite = this.triggerThermite;
        return retVal;
    }
}