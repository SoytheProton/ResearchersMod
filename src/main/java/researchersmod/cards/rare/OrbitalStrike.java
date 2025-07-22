package researchersmod.cards.rare;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.actions.OrbitalStrikeAction;
import researchersmod.cards.BaseCard;
import researchersmod.cards.colorless.OrbitalBeacon;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class OrbitalStrike extends BaseCard {
    public static final String ID = makeID(OrbitalStrike.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ALL_ENEMY,
            -1
    );


    public OrbitalStrike() {
        super(ID, info);
        setDamage(12);
        this.cardsToPreview = new OrbitalBeacon();
        this.tags.add(CardTags.STRIKE);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new OrbitalStrikeAction(Wiz.p(),this,freeToPlayOnce,energyOnUse,damage));
    }
}