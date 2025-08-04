package researchersmod.cards.colorless;

import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.ExperimentCard;
import researchersmod.powers.experiments.CentrifugeExperiment;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

@NoCompendium
public class DummyExperiment extends ExperimentCard {
    public AbstractCard targetCard;
    public static final String ID = makeID(DummyExperiment.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0
    );
    public DummyExperiment() {
        super(ID, info, 2,1);
    }

    public void applyPowers() {
        if(targetCard == null) {
            super.applyPowers();
            return;
        }
        this.portrait = targetCard.portrait;
        this.jokePortrait = targetCard.jokePortrait;
        this.name = targetCard.name;
        this.color = targetCard.color;
        this.type = targetCard.type;
        this.rarity = targetCard.rarity;
        String fakeName = targetCard.name;
        fakeName = fakeName.replace(" "," *");
        String type = targetCard.type.toString().charAt(0) + targetCard.type.toString().toLowerCase().substring(1);
        this.rawDescription = String.format(cardStrings.DESCRIPTION,type,fakeName);
        super.applyPowers();
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new CentrifugeExperiment(p,this.trial,this,targetCard));
    }
}
