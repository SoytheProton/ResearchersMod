package researchersmod.cards.uncommon;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cardmods.EthericMod;
import researchersmod.cards.BaseCard;
import researchersmod.cards.colorless.IonTail;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

import java.util.ArrayList;
import java.util.List;

public class CometStrike extends BaseCard {
    public static final String ID = makeID(CometStrike.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );

    public CometStrike() {
        super(ID, info);
        setDamage(16);
        cardsToPreview = new IonTail();
        this.exhaust = true;
        this.tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        AbstractCard ionTail = new IonTail();
        if(upgraded)
            ionTail.upgrade();
        Wiz.atb(new MakeTempCardInHandAction(ionTail));
    }

    public void upgrade() {
        if(!upgraded) {
            super.upgrade();
            cardsToPreview.upgrade();
        }
    }
}
