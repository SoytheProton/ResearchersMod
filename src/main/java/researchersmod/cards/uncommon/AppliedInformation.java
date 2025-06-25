package researchersmod.cards.uncommon;

import basemod.devcommands.draw.Draw;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class AppliedInformation extends BaseCard {
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
        setMagic(3);
    }

    public void applyPowers() {
        int cost = 4 - Researchers.expsTerminatedThisCombat;
        if (cost < 0)
            cost = 0;
        this.cost = cost;
        if(this.cost != 4)
            this.isCostModified = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(this.magicNumber));
    }
}


