package researchersmod.cards.rare;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.Researchers;
import researchersmod.cards.BaseCard;
import researchersmod.cards.status.MagmaBurn;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.VolcanicResearchPower;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

import java.util.ArrayList;
import java.util.List;

public class VolcanicResearch extends BaseCard {
    public static final String ID = makeID(VolcanicResearch.class.getSimpleName());
    private static ArrayList<TooltipInfo> ToolTip;
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    public VolcanicResearch() {
        super(ID, info);
        setMagic(1);
        setCostUpgrade(1);
        cardsToPreview = new MagmaBurn();
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        if(ToolTip == null)
        {
            ToolTip = new ArrayList<>();
            ToolTip.add(new TooltipInfo(Researchers.keywords.get("Etheric").PROPER_NAME, Researchers.keywords.get("Etheric").DESCRIPTION));
        }
        return ToolTip;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new MakeTempCardInDrawPileAction(new MagmaBurn(),3,true,true));
        addToBot(new ApplyPowerAction(p, p, new VolcanicResearchPower(p,magicNumber),magicNumber));
    }
}
