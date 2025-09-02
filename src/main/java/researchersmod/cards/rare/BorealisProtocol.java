package researchersmod.cards.rare;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.BorealisProtocolPower;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;
import researchersmod.vfx.VerticalAura2Effect;

public class BorealisProtocol extends BaseCard {
    public static final String ID = makeID(BorealisProtocol.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            -2
    );

    public BorealisProtocol() {
        super(ID, info);
        setMagic(2,-1);
        setPhase(true);
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new VerticalAura2Effect(new Color(33.0F/255.0F, 1.0F, 188.0F/255.0F, 0.5F),p.drawX,p.drawY)));
        addToBot(new VFXAction(new VerticalAura2Effect(new Color(0F, 214.0F/255.0F, 214.0F/255.0F, 0.5F),p.drawX,p.drawY)));
        addToBot(new VFXAction(new VerticalAura2Effect(new Color(255F/255F, 77.0F/255.0F, 178.0F/255.0F, 0.5F),p.drawX,p.drawY)));
        addToBot(new ApplyPowerAction(p, p, new BorealisProtocolPower(p)));
    }

    public void triggerWhenDrawn() {
        Wiz.atb(new LoseEnergyAction(this.magicNumber));
    }
}
