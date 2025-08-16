package researchersmod.cards.rare;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import researchersmod.actions.unique.CatalyticAfterburnerAction;
import researchersmod.cards.BaseCard;
import researchersmod.cards.status.PlasmicEnergy;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

import java.util.Objects;

public class CatalyticAfterburner extends BaseCard {
    public static final String ID = makeID(CatalyticAfterburner.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ALL_ENEMY,
            1
    );


    public CatalyticAfterburner() {
        super(ID, info);
        setDamage(4,1);
        this.cardsToPreview = new PlasmicEnergy();
    }

    public void applyPowers() {
        super.applyPowers();
        int i = 0;
        for(AbstractCard c : Wiz.p().exhaustPile.group) {
            if(Objects.equals(c.cardID, PlasmicEnergy.ID))
                i++;
        }
        String plural = cardStrings.EXTENDED_DESCRIPTION[1];
        if(i == 1)
            plural = cardStrings.EXTENDED_DESCRIPTION[2];
        this.rawDescription = cardStrings.DESCRIPTION + String.format(cardStrings.EXTENDED_DESCRIPTION[0],i,plural);
        initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean playedVFX = false;
        for(AbstractCard c : p.exhaustPile.group) {
            if(Objects.equals(c.cardID, PlasmicEnergy.ID)) {
                if(!playedVFX) {
                    playedVFX = true;
                    CardCrawlGame.sound.play("GHOST_FLAMES");
                    AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(Color.FIREBRICK));
                }
                Wiz.atb(new CatalyticAfterburnerAction(this));
            }
        }
    }
}