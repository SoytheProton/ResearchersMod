package researchersmod.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import researchersmod.cards.colorless.DataRequest;
import researchersmod.character.ResearchersCharacter;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.util.Wiz;

import static researchersmod.Researchers.makeID;

public class DatabaseTablet extends BaseRelic {
    public static final String ID = makeID(DatabaseTablet.class.getSimpleName());

    public DatabaseTablet() {
        super(ID, RelicTier.STARTER, LandingSound.CLINK);
        this.pool = ResearchersCharacter.Meta.CARD_COLOR;
    }
    @Override
    public void atBattleStart() {
        flash();
        addToBot(new RelicAboveCreatureAction(Wiz.p(), this));
        addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractCard tmp = new DataRequest();
                tmp.dontTriggerOnUseCard = true;
                tmp.applyPowers();
                tmp.use(Wiz.adp(),(AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng));
                ExperimentCardManager.addExperiment(tmp);
                this.isDone = true;
            }
        });
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(DatabaseTablet.ID);
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
