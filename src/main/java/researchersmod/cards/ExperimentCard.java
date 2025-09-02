package researchersmod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import researchersmod.fields.ExperimentFields;
import researchersmod.util.CardStats;

import static researchersmod.util.GeneralUtils.removePrefix;
import static researchersmod.util.TextureLoader.getCardTextureString;

public abstract class ExperimentCard extends BaseCard{
    public ExperimentCard(String ID, CardStats info, int basetrial) {
        this(ID, info, basetrial, 0, getCardTextureString(removePrefix(ID), info.cardType));
    }

    public ExperimentCard(String ID, CardStats info, int basetrial, int upgradedTrial) {
        this(ID, info, basetrial, upgradedTrial, getCardTextureString(removePrefix(ID), info.cardType));
    }

    public ExperimentCard(String ID, CardStats info, int basetrial, int upgradedTrial, String cardImage) {
        this(ID, info.baseCost, info.cardType, info.cardTarget, info.cardRarity, info.cardColor, basetrial, upgradedTrial, cardImage);
    }

    public ExperimentCard(String ID, int cost, CardType cardType, CardTarget target, CardRarity rarity, CardColor color, int basetrial, int upgradedTrial) {
        this(ID, cost, cardType, target, rarity, color, basetrial, upgradedTrial, getCardTextureString(removePrefix(ID), cardType));
    }

    public ExperimentCard(String ID, int cost, CardType cardType, CardTarget target, CardRarity rarity, CardColor color, int basetrial, int upgradedtrial, String cardImage) {
        super(ID, cost, cardType, target, rarity, color, cardImage);
        ExperimentFields.playExperiment.set(this,true);
        baseTrial = basetrial;
        trial = basetrial;
        upgradedTrial = upgradedtrial;
        setCustomVar("Trial",VariableType.MAGIC,trial,upgradedTrial,(card, m, base) -> ((ExperimentCard)card).trial);
        colorCustomVar("Trial", Settings.BLUE_TEXT_COLOR,Settings.GREEN_TEXT_COLOR,Settings.RED_TEXT_COLOR,Settings.GREEN_TEXT_COLOR);
    }

    public int trial = 1;
    public int upgradedTrial;
    public int baseTrial = 1;

    @Override
    public void upgrade() {
        if (!upgraded && upgradedTrial != 0) {
            this.trial = this.baseTrial + this.upgradedTrial;
            this.baseTrial = this.baseTrial + this.upgradedTrial;
        }
        super.upgrade();
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard retVal = super.makeStatEquivalentCopy();
        ((ExperimentCard)retVal).trial = this.trial;
        ((ExperimentCard)retVal).baseTrial = this.baseTrial;
        return retVal;
    }

    public AbstractCard makeTrialCopy(int trialNumber) {
        setCustomVar("Trial",trialNumber);
        AbstractCard retVal = super.makeStatEquivalentCopy();
        ((ExperimentCard)retVal).trial = this.trial;
        ((ExperimentCard)retVal).baseTrial = this.baseTrial;
        setCustomVar("Trial",this.baseTrial);
        return retVal;
    }
}
