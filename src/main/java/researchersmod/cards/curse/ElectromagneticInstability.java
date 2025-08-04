package researchersmod.cards.curse;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.powers.InstabilityPower;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class ElectromagneticInstability extends BaseCard {
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

    public void triggerWhenDrawn() {
        Wiz.applyToSelf(new InstabilityPower(Wiz.p(),1));
    }
}