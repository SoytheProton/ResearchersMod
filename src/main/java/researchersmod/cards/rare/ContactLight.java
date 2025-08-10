package researchersmod.cards.rare;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.Researchers;
import researchersmod.actions.unique.ContactLightAction;
import researchersmod.cards.BaseCard;
import researchersmod.cards.status.PlasmicEnergy;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

import java.util.ArrayList;
import java.util.List;

public class ContactLight extends BaseCard {
    public static final String ID = makeID(ContactLight.class.getSimpleName());
    private ArrayList<TooltipInfo> ToolTip;
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );


    public ContactLight() {
        super(ID, info);
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
        Wiz.atb(new ContactLightAction(p, upgraded));
    }
}
