package researchersmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.Researchers;
import researchersmod.actions.unique.AltercatedBlueprintAction;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class AltercatedBlueprint extends BaseCard {
    public static final String ID = makeID(AltercatedBlueprint.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );


    public AltercatedBlueprint() {
        super(ID, info);
        setBlock(7,3);
    }

    public void applyPowers() {
        super.applyPowers();
        if(Researchers.LastPhasedCard != null) {
            String cardName = Researchers.LastPhasedCard.name.replace(" ", " [#efc851]");
            this.cardsToPreview = Researchers.LastPhasedCard.makeStatEquivalentCopy();
            this.rawDescription = cardStrings.DESCRIPTION + String.format(cardStrings.EXTENDED_DESCRIPTION[0], cardName);
            initializeDescription();
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new GainBlockAction(p, block));
        Wiz.atb(new AltercatedBlueprintAction());
    }
}
