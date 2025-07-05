package researchersmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class HeavyContainment extends BaseCard {
    public static final String ID = makeID(HeavyContainment.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public HeavyContainment() {
        super(ID, info);
        setBlock(6,3);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int i = 0;
        for(AbstractCard c : p.hand.group) {
            Wiz.atb(new ExhaustSpecificCardAction(c,p.hand));
            i++;
        }
        Wiz.atb(new GainBlockAction(p, i * this.block));
        Wiz.applyToSelf(new NextTurnBlockPower(p, (i*this.block)/2,"Heavy Containment"));
    }
}
