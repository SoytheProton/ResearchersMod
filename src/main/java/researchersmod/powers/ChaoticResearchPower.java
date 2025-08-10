package researchersmod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.StrengthPower;
import researchersmod.Researchers;
import researchersmod.cards.status.MagmaBurn;
import researchersmod.util.GeneralUtils;
import researchersmod.util.TextureLoader;
import researchersmod.util.Wiz;

public class ChaoticResearchPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(ChaoticResearchPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;

    public ChaoticResearchPower(AbstractCreature owner, int amt) {
        super(POWER_ID, TYPE, TURNBASED, owner, null, amt,true,false);
        String unPrefixed = GeneralUtils.removePrefix(VolcanicResearchPower.POWER_ID);
        Texture normalTexture = TextureLoader.getPowerTexture(unPrefixed);
        Texture hiDefImage = TextureLoader.getHiDefPowerTexture(unPrefixed);
        region128 = new TextureAtlas.AtlasRegion(hiDefImage, 0, 0, hiDefImage.getWidth(), hiDefImage.getHeight());
        if (normalTexture != null)
            region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        Wiz.atb(new MakeTempCardInDrawPileAction(new MagmaBurn(),this.amount,true,true));
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if(card.type == AbstractCard.CardType.STATUS) {
            flash();
            Wiz.atb(new ApplyPowerAction(owner,owner, new StrengthPower(owner,this.amount)));
            Wiz.atb(new DrawCardAction(this.amount));
        }
    }

    public void updateDescription() {
        String plural = "s";
        if(this.amount == 1) plural = "";
        this.description = String.format(DESCRIPTIONS[0],this.amount,plural,this.amount,this.amount,plural);
    }
}
