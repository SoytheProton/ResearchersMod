package researchersmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DescriptionLine;
import researchersmod.Researchers;
import researchersmod.util.CardStats;

import java.util.ArrayList;

import static researchersmod.util.GeneralUtils.removePrefix;
import static researchersmod.util.TextureLoader.getCardTextureString;

public abstract class ExperimentCard extends BaseCard{
    public ExperimentCard(String ID, CardStats info, int basetrial) {
        this(ID, info, basetrial, getCardTextureString(removePrefix(ID), info.cardType));
    }

    public ExperimentCard(String ID, CardStats info, int basetrial, String cardImage) {
        this(ID, info.baseCost, info.cardType, info.cardTarget, info.cardRarity, info.cardColor, basetrial, cardImage);
    }

    public ExperimentCard(String ID, int cost, CardType cardType, CardTarget target, CardRarity rarity, CardColor color, int basetrial) {
        this(ID, cost, cardType, target, rarity, color, basetrial, getCardTextureString(removePrefix(ID), cardType));
    }

    public ExperimentCard(String ID, int cost, CardType cardType, CardTarget target, CardRarity rarity, CardColor color, int basetrial, String cardImage) {
        super(ID, cost, cardType, target, rarity, color, cardImage);
        tags.add(Researchers.EXPERIMENT);
        BaseTrial = basetrial;
        Trial = basetrial;
        setCustomVar("Trial",VariableType.MAGIC,Trial,UpgradedTrial,(card, m, base) -> { return Trial; });
    }

    public int Trial = 1;
    public int UpgradedTrial;
    public int BaseTrial = 1;

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
