package researchersmod.cards.uncommon;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cardmods.BetterEtherealMod;
import researchersmod.cardmods.EthericMod;
import researchersmod.cardmods.PhaseMod;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

import java.util.ArrayList;
import java.util.List;

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
        setDamage(6);
        setPhase(true);
        setEtheric(2);
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

    public void upgrade() {
        if(!upgraded) {
            super.upgrade();
            CardModifierManager.removeModifiersById(this,EthericMod.ID,true);
            CardModifierManager.addModifier(this, new BetterEtherealMod());
        }
    }
}
