package researchersmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DescriptionLine;
import researchersmod.Researchers;
import researchersmod.util.CardStats;

import java.util.ArrayList;

public abstract class ExperimentCard extends BaseCard{
    public ExperimentCard(String ID, CardStats info) {
        super(ID, info);
        tags.add(Researchers.EXPERIMENT);
    }

    public ExperimentCard(String ID, CardStats info, String cardImage) {
        super(ID, info, cardImage);
        tags.add(Researchers.EXPERIMENT);
    }

    public ExperimentCard(String ID, int cost, CardType cardType, CardTarget target, CardRarity rarity, CardColor color) {
        super(ID, cost, cardType, target, rarity, color);
        tags.add(Researchers.EXPERIMENT);
    }

    public ExperimentCard(String ID, int cost, CardType cardType, CardTarget target, CardRarity rarity, CardColor color, String cardImage) {
        super(ID, cost, cardType, target, rarity, color, cardImage);
        tags.add(Researchers.EXPERIMENT);
    }

    public int Trial = 1;

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard retVal = super.makeStatEquivalentCopy();
        System.out.println("Original:" + ((ExperimentCard)retVal).Trial);
        ((ExperimentCard)retVal).Trial = this.Trial;
        System.out.println("Copy:" + this.Trial);
        return retVal;
    }

    public boolean shouldUpgradeDescription = false;

}
