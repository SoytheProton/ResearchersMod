package researchersmod.cards.curse;

import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.patches.VanishingCardPatch;
import researchersmod.util.CardStats;

@NoCompendium
public class CommandExecuted extends BaseCard implements VanishingCardPatch.VanishingCard {
    public static final String ID = makeID(CommandExecuted.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.CURSE,
            CardType.STATUS,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            -2
    );

    public CommandExecuted() {
        super(ID, info);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    public void triggerOnGlowCheck() {
        this.glowColor = Color.RED.cpy();
        if(!isGlowing) this.beginGlowing();
    }
}