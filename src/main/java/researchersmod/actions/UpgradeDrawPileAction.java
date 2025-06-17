package researchersmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import researchersmod.util.Wiz;

import java.util.ArrayList;

public class UpgradeDrawPileAction extends AbstractGameAction {

    private ArrayList<AbstractCard> Upgradable = new ArrayList<>();
    private AbstractCard Card;
    private int amt;
    private boolean SpecificCardType = false;
    private AbstractCard.CardRarity rarity;

    public UpgradeDrawPileAction(int amount) {
        this(amount,false,null);
    }


    public UpgradeDrawPileAction(int amount, boolean shouldCheckRarity, AbstractCard.CardRarity Rarity) {
        this.duration = 1.5F;
        SpecificCardType = shouldCheckRarity;
        rarity = Rarity;
        amt = amount;
    }

    public void update() {
        for(AbstractCard c : Wiz.adp().drawPile.group) {
            if(c.canUpgrade() && (!SpecificCardType || c.rarity == rarity)) {
                Upgradable.add(c);
            }
        }
        int y = Upgradable.size();
        for (int i = amt; i > 0;i--) {
            if (Upgradable.isEmpty()) break;
            Card = Upgradable.get(AbstractDungeon.cardRandomRng.random(Upgradable.size()-1));
            Upgradable.remove(Card);
            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(Card));
            Card.upgrade();
            Card.applyPowers();
            Card.superFlash();
        }
        if (y > 0) AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        this.isDone = true;
    }
}

