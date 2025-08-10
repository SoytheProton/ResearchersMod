package researchersmod.cards.rare;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.actions.unique.CentrifugeAction;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class Centrifuge extends BaseCard {
    public static final String ID = makeID(Centrifuge.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            0
    );
    public Centrifuge() {
        super(ID, info);
        setMagic(2,1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(meetConditions(p)) Wiz.atb(new CentrifugeAction(p,CardType.ATTACK,cardStrings.EXTENDED_DESCRIPTION[0],upgraded));
    }

    private boolean meetConditions(AbstractPlayer p) {
        boolean ATTACK = false; boolean SKILL = false; boolean POWER = false; boolean STATUS = false; boolean CURSE = false;
        for(AbstractCard c : p.hand.group) {
            if(c != this) switch(c.type) {
                case ATTACK:
                    ATTACK = true;
                    break;
                case SKILL:
                    SKILL = true;
                    break;
                case POWER:
                    POWER = true;
                    break;
                case STATUS:
                    STATUS = true;
                    break;
                case CURSE:
                    CURSE = true;
                    break;
            }
        }
        return (ATTACK && SKILL && POWER && STATUS && CURSE);
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        }
        if (!meetConditions(p)) {
            canUse = false;
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[1];
        }
        return canUse;
    }
}

