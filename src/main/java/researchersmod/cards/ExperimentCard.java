package researchersmod.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DescriptionLine;
import com.megacrit.cardcrawl.core.Settings;
import researchersmod.Researchers;
import researchersmod.util.CardStats;

import java.util.ArrayList;

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
        tags.add(Researchers.EXPERIMENT);
        BaseTrial = basetrial;
        Trial = basetrial;
        upgradedTrial = upgradedtrial;
        setCustomVar("Trial",VariableType.MAGIC,Trial,upgradedTrial,(card, m, base) -> ((ExperimentCard)card).Trial);
        colorCustomVar("Trial", Settings.BLUE_TEXT_COLOR,Settings.BLUE_TEXT_COLOR,Settings.BLUE_TEXT_COLOR,Settings.BLUE_TEXT_COLOR);
    }

    public int Trial = 1;
    public int upgradedTrial;
    public int BaseTrial = 1;

    @Override
    public void upgrade() {
        if (!upgraded && upgradedTrial != 0) {
            this.Trial = this.BaseTrial + this.upgradedTrial;
            this.BaseTrial = this.BaseTrial + this.upgradedTrial;
        }
        super.upgrade();
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard retVal = super.makeStatEquivalentCopy();
        ((ExperimentCard)retVal).Trial = this.Trial;
        retVal.initializeDescription();
        return retVal;
    }

}
