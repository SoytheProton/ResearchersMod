package researchersmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.interfaces.ExperimentInterfaces;
import researchersmod.util.CardStats;

public class AppliedInformation extends BaseCard implements ExperimentInterfaces.OnTerminateInterface {
    public static final String ID = makeID(AppliedInformation.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            4
    );

    public AppliedInformation() {
        super(ID, info);
        setMagic(3,1);
        if (CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null) {
            configureCostsOnNewCard();
        }
    }
    public void configureCostsOnNewCard() {
        updateCost(-Researchers.expsTerminatedThisCombat);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(this.magicNumber));
    }

    @Override
    public void onTerminate(AbstractPower power) {
        updateCost(-1);
    }
}


