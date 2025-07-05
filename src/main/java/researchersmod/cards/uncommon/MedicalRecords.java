package researchersmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

import java.util.ArrayList;

public class MedicalRecords extends BaseCard {
    public static final String ID = makeID(MedicalRecords.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    public MedicalRecords() {
        super(ID, info);
        setMagic(3,1);
        this.tags.add(CardTags.HEALING);
        setEthereal(true);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int i = 0;
        ArrayList<AbstractCard> List = new ArrayList<AbstractCard>() {{
            addAll(p.discardPile.group);
        }};
        if(upgraded)
            List.addAll(p.exhaustPile.group);
        for (AbstractCard c : List) {
            if(c.type == CardType.STATUS)
                i++;
        }
        Wiz.atb(new HealAction(p, p, i));
    }
}
