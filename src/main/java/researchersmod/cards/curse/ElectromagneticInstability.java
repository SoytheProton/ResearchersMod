package researchersmod.cards.curse;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.patches.AddCardToPilePatch;
import researchersmod.powers.InstabilityPower;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class ElectromagneticInstability extends BaseCard implements AddCardToPilePatch.AddToPileInterface {
    public static final String ID = makeID(ElectromagneticInstability.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.CURSE,
            CardType.CURSE,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            -2
    );

    public ElectromagneticInstability() {
        super(ID, info);
        SoulboundField.soulbound.set(this,true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void onAddCard(CardGroup group, AbstractCard card) {
        if(group == Wiz.p().hand) {
            Wiz.applyToSelf(new InstabilityPower(Wiz.p(),1));
        }
    }
}