package researchersmod.cards.deprecated;

import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;

import java.util.ArrayList;

public class OwnTempo extends BaseCard {
    public static final String ID = makeID(OwnTempo.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.RARE,
            AbstractCard.CardTarget.SELF,
            3
    );

    public OwnTempo() {
        super(ID, info);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
        stanceChoices.add(new freeattacks());
        stanceChoices.add(new freeskills());
        stanceChoices.add(new freepowers());
        addToBot(new ChooseOneAction(stanceChoices));
    }
    public void upgrade() {
        if (!this.upgraded) {
            super.upgrade();
            upgradeBaseCost(2);
        }
    }

}
