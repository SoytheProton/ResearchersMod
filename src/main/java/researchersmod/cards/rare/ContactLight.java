package researchersmod.cards.rare;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.actions.ContactLightAction;
import researchersmod.cards.BaseCard;
import researchersmod.cards.status.PlasmicEnergy;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class ContactLight extends BaseCard {
    public static final String ID = makeID(ContactLight.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );


    public ContactLight() {
        super(ID, info);
        this.cardsToPreview = new PlasmicEnergy();
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new ContactLightAction(p,upgraded));
    }

    public void upgrade() {
        if(!upgraded) {
            super.upgrade();
            cardsToPreview.upgrade();
        }
    }
}
