package researchersmod.cards.basic;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.ExperimentCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.experiments.DevelopmentExperiment;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class Development extends ExperimentCard {

    public static final String ID = makeID(Development.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.BASIC,
            AbstractCard.CardTarget.SELF,
            2
    );

    public Development() {
        super(ID, info,1);
        setBlock(8, 3);
        setMagic(1,1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
        Wiz.atb(new ApplyPowerAction(p,p, new DevelopmentExperiment(p, this.trial,this,this.magicNumber)));
    }
}
