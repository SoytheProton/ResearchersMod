package researchersmod.cards.basic;

import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.cards.optioncards.gaindexterity;
import researchersmod.cards.optioncards.gainstrength;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;

import java.util.ArrayList;

public class Research extends BaseCard {
    public static final String ID = makeID(Research.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.POWER,
            AbstractCard.CardRarity.BASIC,
            AbstractCard.CardTarget.SELF,
            0
    );

    public Research() {
        super(ID, info);
        setMagic(1,1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
        stanceChoices.add(new gainstrength());
        stanceChoices.add(new gaindexterity());
        if(upgraded) {
            for (AbstractCard c : stanceChoices)
                c.upgrade();
        }
        addToBot(new ChooseOneAction(stanceChoices));
    }
}