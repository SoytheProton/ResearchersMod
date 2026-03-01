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
import researchersmod.character.ResearchersCharacter;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

import java.util.ArrayList;

public class CatalyticAfterburner extends BaseCard {
    public static final String ID = makeID(CatalyticAfterburner.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ALL_ENEMY,
            2
    );


    public CatalyticAfterburner() {
        super(ID, info);
        setDamage(6,2);
    }

    public void applyPowers() {
        super.applyPowers();
        int i = 0;
        for (AbstractCard c : new ArrayList<AbstractCard>() {{
            addAll(AbstractDungeon.player.hand.group);
            addAll(AbstractDungeon.player.discardPile.group);
            addAll(AbstractDungeon.player.drawPile.group);
            addAll(AbstractDungeon.player.exhaustPile.group);
            addAll(ExperimentCardManager.experiments.group);
        }}) if(c.type == CardType.STATUS) i++;

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
        for (AbstractCard c : new ArrayList<AbstractCard>() {{
            addAll(AbstractDungeon.player.hand.group);
            addAll(AbstractDungeon.player.discardPile.group);
            addAll(AbstractDungeon.player.drawPile.group);
            addAll(AbstractDungeon.player.exhaustPile.group);
            addAll(ExperimentCardManager.experiments.group);
        }}) if(c.type == CardType.STATUS) {
            if(!playedVFX) {
                playedVFX = true;
                CardCrawlGame.sound.play("GHOST_FLAMES");
                AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(Color.FIREBRICK));
            }
            Wiz.atb(new CatalyticAfterburnerAction(this));
        }
    }
}