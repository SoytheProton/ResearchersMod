package researchersmod.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.actions.common.BetterSelectCardsAction;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class ClipboardSlap extends BaseCard {
    public static final String ID = makeID(ClipboardSlap.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.ATTACK,
            AbstractCard.CardRarity.COMMON,
            AbstractCard.CardTarget.ENEMY,
            1
    );

    public ClipboardSlap() {
        super(ID, info);
        setDamage(8, 2);
    }

    public void applyPowers() {
        super.applyPowers();
        if(!Wiz.p().discardPile.isEmpty() && !this.upgraded) this.cardsToPreview = Wiz.p().discardPile.getTopCard().makeStatEquivalentCopy();
        else this.cardsToPreview = null;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        if(upgraded) Wiz.atb(new BetterSelectCardsAction(p.discardPile.group,1, cardStrings.EXTENDED_DESCRIPTION[0],false,(c -> true),(cards) -> {
            for (AbstractCard c : cards) {
                Wiz.att(new ExhaustSpecificCardAction(c, p.discardPile));
            }
        }));
        else if(!Wiz.p().discardPile.isEmpty())  {
            AbstractCard card = p.discardPile.getTopCard();
            card.current_x = Settings.WIDTH / 2f;
            card.current_y   = Settings.HEIGHT / 2f;
            card.target_x = Settings.WIDTH / 2f;
            card.target_y = Settings.HEIGHT / 2f;
            p.discardPile.moveToExhaustPile(card);
        }
    }
}
