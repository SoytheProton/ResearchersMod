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
        setCustomVar("Trial",VariableType.MAGIC,Trial,UpgradedTrial);
    }

    public ExperimentCard(String ID, CardStats info, String cardImage) {
        super(ID, info, cardImage);
        tags.add(Researchers.EXPERIMENT);
        setCustomVar("Trial",VariableType.MAGIC,Trial,UpgradedTrial);
    }

    public ExperimentCard(String ID, int cost, CardType cardType, CardTarget target, CardRarity rarity, CardColor color) {
        super(ID, cost, cardType, target, rarity, color);
        tags.add(Researchers.EXPERIMENT);
        setCustomVar("Trial",VariableType.MAGIC,Trial,UpgradedTrial);
    }

    public ExperimentCard(String ID, int cost, CardType cardType, CardTarget target, CardRarity rarity, CardColor color, String cardImage) {
        super(ID, cost, cardType, target, rarity, color, cardImage);
        tags.add(Researchers.EXPERIMENT);
        setCustomVar("Trial",VariableType.MAGIC,Trial,UpgradedTrial);
    }

    public int Trial = 1;
    public int UpgradedTrial = 0;

    @Override
    public void upgrade() {
        super.upgrade();
        if (!this.upgraded) {
            this.Trial = this.Trial + this.UpgradedTrial;
        }
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard retVal = super.makeStatEquivalentCopy();
        ((ExperimentCard)retVal).Trial = this.Trial;
        retVal.initializeDescription();
        return retVal;
    }

    public boolean shouldUpgradeDescription = false;

}
