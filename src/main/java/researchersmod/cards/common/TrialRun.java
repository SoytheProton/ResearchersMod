package researchersmod.cards.common;

import basemod.devcommands.draw.Draw;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import researchersmod.cardmods.PhaseMod;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class TrialRun extends BaseCard {
    public static final String ID = makeID(TrialRun.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            0
    );
    public TrialRun() {
        super(ID, info);
        setMagic(0);
        this.upgPhase = true;
    }

    private int timesPlayedThisCombat() {
        int i = 0;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if(c == this) {
                i++;
            }
        }
        return i;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(p, 1));
    }

    public void applyPowers() {
        if (timesPlayedThisCombat() > 1)
            this.rawDescription =  cardStrings.DESCRIPTION + " NL " + cardStrings.EXTENDED_DESCRIPTION[1];
        else if (timesPlayedThisCombat() == 1)
            this.rawDescription =  cardStrings.DESCRIPTION + " NL " + cardStrings.EXTENDED_DESCRIPTION[0];
        else
            this.rawDescription = cardStrings.DESCRIPTION;
        this.baseMagicNumber = timesPlayedThisCombat();
        initializeDescription();
        super.applyPowers();
    }

    public void onMoveToDiscard() {
        applyPowers();
    }

    public void triggerOnEndOfPlayerTurn() {
        applyPowers();
    }

    @Override
    public void triggerOnExhaust() {
        addToBot(new DrawCardAction(Wiz.adp(),timesPlayedThisCombat()));
    }
}
