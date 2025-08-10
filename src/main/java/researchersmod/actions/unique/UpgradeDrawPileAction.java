package researchersmod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import researchersmod.util.Wiz;

import java.util.ArrayList;

public class UpgradeDrawPileAction extends AbstractGameAction {

    private ArrayList<AbstractCard> upgradable = new ArrayList<>();
    private AbstractCard card;
    private final int amt;
    private final boolean shouldCheckRarity;
    private final AbstractCard.CardRarity rarity;

    public UpgradeDrawPileAction(int amount) {
        this(amount,false,null);
    }

    public UpgradeDrawPileAction(int amount, boolean shouldCheckRarity, AbstractCard.CardRarity Rarity) {
        this.duration = 1.5F;
        this.shouldCheckRarity = shouldCheckRarity;
        rarity = Rarity;
        amt = amount;
    }

    public void update() {
        for(AbstractCard c : Wiz.adp().drawPile.group) {
            if(c.canUpgrade() && (!shouldCheckRarity || c.rarity == rarity)) {
                upgradable.add(c);
            }
        }
        int y = upgradable.size();
        for (int i = amt; i > 0;i--) {
            if (upgradable.isEmpty()) break;
            card = upgradable.get(AbstractDungeon.cardRandomRng.random(upgradable.size()-1));
            upgradable.remove(card);
            card.upgrade();
            card.applyPowers();
            AbstractCard copy = card.makeStatEquivalentCopy();
            AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(copy));
            copy.superFlash();
        }
        if (y > 0) AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        this.isDone = true;
    }
}

