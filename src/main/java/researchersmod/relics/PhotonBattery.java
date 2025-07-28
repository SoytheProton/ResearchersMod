package researchersmod.relics;

import basemod.helpers.CardPowerTip;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.cards.status.PlasmicEnergy;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.Wiz;

import static researchersmod.Researchers.makeID;

public class PhotonBattery extends BaseRelic {
    public static final String ID = makeID(PhotonBattery.class.getSimpleName());
    private final AbstractPlayer p = Wiz.p();

    public PhotonBattery() {
        super(ID, RelicTier.COMMON, LandingSound.HEAVY);
        AbstractCard tmp = new PlasmicEnergy();
        tmp.upgrade();
        this.tips.add(new CardPowerTip(tmp));
        this.pool = ResearchersCharacter.Meta.CARD_COLOR;
    }
    @Override
    public void atBattleStart() {
        flash();
        addToBot((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature) p, this));
        AbstractCard tmp = new PlasmicEnergy();
        tmp.upgrade();
        Wiz.atb(new MakeTempCardInHandAction(tmp));
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
