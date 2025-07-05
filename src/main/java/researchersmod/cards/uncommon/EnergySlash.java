package researchersmod.cards.uncommon;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cardmods.PhaseMod;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;

public class EnergySlash extends BaseCard {
    public static final String ID = makeID(EnergySlash.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );


    public EnergySlash() {
        this(0);
    }
    public EnergySlash(int upgrades) {
        super(ID, info);
        setDamage(6);
        setPhase(true,true);
        this.exhaust = true;
        this.timesUpgraded = upgrades;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }

    public void upgrade() {
        if(upgraded) {
            CardModifierManager.addModifier(this, new PhaseMod(true));
            this.timesUpgraded++;
        }
        super.upgrade();
        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        initializeTitle();
    }

    public AbstractCard makeCopy() {
        AbstractCard card = super.makeCopy();
        card.timesUpgraded = this.timesUpgraded;
        return card;
    }
}
