package researchersmod.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import researchersmod.cards.colorless.DataRequest;
import researchersmod.character.ResearchersCharacter;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.util.Wiz;

import static researchersmod.Researchers.makeID;

public class DatabaseTablet extends BaseRelic {
    public static final String ID = makeID(DatabaseTablet.class.getSimpleName());
    private boolean activated;

    public DatabaseTablet() {
        super(ID, RelicTier.STARTER, LandingSound.CLINK);
        this.pool = ResearchersCharacter.Meta.CARD_COLOR;
    }
    @Override
    public void atBattleStart() {
        flash();
        addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractCard tmp = new DataRequest();
                tmp.current_y = -200.0F * Settings.scale;
                tmp.target_x = Settings.WIDTH / 2.0F;
                tmp.target_y = Settings.HEIGHT / 2.0F;
                tmp.targetAngle = 0.0F;
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
