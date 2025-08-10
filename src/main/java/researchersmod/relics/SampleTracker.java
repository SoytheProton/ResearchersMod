package researchersmod.relics;

import basemod.helpers.CardPowerTip;
import com.evacipated.cardcrawl.mod.stslib.patches.bothInterfaces.OnCreateCardInterface;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import researchersmod.cards.status.BurntDocument;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.Wiz;

import java.util.Objects;

import static researchersmod.Researchers.makeID;

public class SampleTracker extends BaseRelic implements OnCreateCardInterface {
    public static final String ID = makeID(SampleTracker.class.getSimpleName());

    public SampleTracker() {
        super(ID, RelicTier.UNCOMMON, LandingSound.FLAT);
        this.pool = ResearchersCharacter.Meta.CARD_COLOR;
        AbstractCard tmp = new BurntDocument();
        tmp.upgrade();
        this.tips.add(new CardPowerTip(tmp));
    }
    @Override
    public void atBattleStart() {
        Wiz.att(new MakeTempCardInHandAction(new BurntDocument(),1));
    }

    @Override
    public void onCreateCard(AbstractCard card) {
        if(Objects.equals(card.cardID, BurntDocument.ID) && !card.upgraded) {
            card.upgrade();
            flash();
        }
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
