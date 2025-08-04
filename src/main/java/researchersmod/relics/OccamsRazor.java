package researchersmod.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import researchersmod.relics.interfaces.AtBlockModifyInterface;

import static researchersmod.Researchers.makeID;

public class OccamsRazor extends BaseRelic implements AtBlockModifyInterface {
    public static final String ID = makeID(OccamsRazor.class.getSimpleName());

    public OccamsRazor() {
        super(ID, RelicTier.UNCOMMON, LandingSound.CLINK);
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public float atDamageModify(float damage, AbstractCard c) {
        return c.rarity == AbstractCard.CardRarity.BASIC ? damage + 2.0F : damage;
    }

    public float atBlockModify(float block, AbstractCard c) {
        return c.rarity == AbstractCard.CardRarity.BASIC ? block + 3.0F : block;
    }
}
