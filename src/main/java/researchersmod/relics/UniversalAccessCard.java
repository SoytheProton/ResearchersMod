package researchersmod.relics;

import basemod.helpers.CardPowerTip;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import researchersmod.cards.colorless.DataOverride;
import researchersmod.character.ResearchersCharacter;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.util.Wiz;

import static researchersmod.Researchers.makeID;

public class UniversalAccessCard extends BaseRelic {
    public static final String ID = makeID(UniversalAccessCard.class.getSimpleName());

    public UniversalAccessCard() {
        super(ID, RelicTier.BOSS, LandingSound.CLINK);
        this.tips.add(new CardPowerTip(new DataOverride()));
        this.pool = ResearchersCharacter.Meta.CARD_COLOR;
    }
    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(DatabaseTablet.ID)) {
            for (int i=0; i<AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(DatabaseTablet.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    @Override
    public void atBattleStart() {
        flash();
        addToTop(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractCard tmp = new DataOverride();
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
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
