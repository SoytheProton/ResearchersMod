package researchersmod.cards.basic;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.Researchers;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.experiments.DevelopmentExperiment;
import researchersmod.util.CardStats;
import researchersmod.util.ExperimentUtil;
import researchersmod.util.Wiz;

public class Development extends BaseCard {

    public static final String ID = makeID(Development.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.BASIC,
            AbstractCard.CardTarget.SELF,
            1
    );

    private static final int BLOCK = 6;
    private static final int UPG_BLOCK = 3;

    public Development() {
        super(ID, info);
        setBlock(BLOCK, UPG_BLOCK);
        this.tags.add(Researchers.EXPERIMENT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
        Wiz.atb(new ApplyPowerAction(p,p, new DevelopmentExperiment(p, 1,this)));
        ExperimentUtil.ExperimentDescription(this,1,upgraded);
        System.out.println("Card Played (shocker)");
        System.out.println(this);
        System.out.println("");
    }
}
