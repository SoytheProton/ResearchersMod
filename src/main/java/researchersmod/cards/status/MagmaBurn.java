package researchersmod.cards.status;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cardmods.ThermiteMod;
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

    public MagmaBurn() {
        super(ID, info);
        setMagic(2);
        ExhaustiveVariable.setBaseValue(this,2);
        cardsToPreview = new Burn();
    }

    public MagmaBurn(boolean madeByThermite) {
        super(ID, info);
        setMagic(2);
        ExhaustiveVariable.setBaseValue(this,2);
        cardsToPreview = new Burn();
        CardModifierManager.addModifier(this,new ThermiteMod());
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
                addToBot((AbstractGameAction)new DamageAllEnemiesAction(AbstractDungeon.player, 2*magicNumber * statusCount, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
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
            this.cost = 1;
            this.baseCost = 1;
            this.costForTurn = 1;
            this.isCostModifiedForTurn = false;
            this.isCostModified = false;
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

    public void triggerOnExhaust() {
        AbstractCard tmp = new Burn();
        if(CardModifierManager.hasModifier(this, ThermiteMod.ID)) CardModifierManager.addModifier(tmp,new ThermiteMod());
        Wiz.atb(new MakeTempCardInDiscardAction(tmp,1));
    }

    public void upgrade() {
        if(!upgraded) {
            super.upgrade();
            ExhaustiveVariable.upgrade(this,1);
        }
    }

    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard retVal = super.makeStatEquivalentCopy();
        return retVal;
    }
}