package researchersmod.cards.uncommon;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.Researchers;
import researchersmod.cards.BaseCard;
import researchersmod.cards.status.PlasmicEnergy;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.TeslaCoilPower;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

import java.util.ArrayList;
import java.util.List;

public class TeslaCoil extends BaseCard {
    public static final String ID = makeID(TeslaCoil.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static ArrayList<TooltipInfo> ToolTip;

    public TeslaCoil() {
        super(ID, info);
        setMagic(1);
        setInnate(false, true);
        this.cardsToPreview = new PlasmicEnergy();
        cardsToPreview.upgrade();
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
        Wiz.applyToSelf(new TeslaCoilPower(p, magicNumber));
    }
}
