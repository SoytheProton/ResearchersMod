package researchersmod.actions.unique;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import researchersmod.actions.common.KillCardAction;
import researchersmod.cards.colorless.DummyExperiment;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.util.Wiz;

public class CentrifugeAction extends AbstractGameAction {
    private final String text;
    private final AbstractCard.CardType type;
    private final AbstractPlayer p;
    private final boolean upgraded;
    public CentrifugeAction(AbstractPlayer p, AbstractCard.CardType type, String text, boolean upgraded) {
        this.p = p;
        this.type = type;
        this.text = text;
        this.upgraded = upgraded;
        this.duration = Settings.ACTION_DUR_FASTER;
        this.startDuration = Settings.ACTION_DUR_FASTER;
    }

    @Override
    public void update() {
        if(this.duration == this.startDuration) addToBot(new SelectCardsInHandAction(1,text,false,false,(c -> (c.type == type)
        ),(cards) -> {
            for (AbstractCard c : cards) {
                Wiz.atb(new KillCardAction(c));
                DummyExperiment card = new DummyExperiment();
                card.targetCard = c;
                card.dontTriggerOnUseCard = true;
                card.current_y = c.current_y;
                card.current_x = c.current_x;
                card.target_x = Settings.WIDTH / 2.0F;
                card.target_y = Settings.HEIGHT / 2.0F;
                card.targetAngle = 0.0F;
                if(upgraded) card.upgrade();
                card.applyPowers();
                card.use((AbstractPlayer) p,(AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng));
                ExperimentCardManager.addExperiment(card);
                AbstractCard.CardType nextType = null;
                switch (type) {
                    case ATTACK :
                        nextType = AbstractCard.CardType.SKILL;
                        break;
                    case SKILL:
                        nextType = AbstractCard.CardType.POWER;
                        break;
                    case POWER:
                        nextType = AbstractCard.CardType.STATUS;
                        break;
                    case STATUS:
                        nextType = AbstractCard.CardType.CURSE;
                        break;
                    default:
                        break;
                }
                if(nextType != null) Wiz.atb(new CentrifugeAction(p,nextType,text,upgraded));
            }
        }));
        tickDuration();
    }
}
