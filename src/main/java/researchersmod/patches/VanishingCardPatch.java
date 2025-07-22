package researchersmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.util.Wiz;

import java.util.Arrays;

public class VanishingCardPatch {
    public interface VanishingCard {}
        @SpirePatch(clz = AbstractRoom.class, method = "endTurn")
        public static class EndTurn {
            @SpirePrefixPatch
            public static void Prefix(AbstractRoom __instance) {
                if (Wiz.isInCombat()) {
                    AbstractPlayer p = AbstractDungeon.player;
                    for (CardGroup cardGroup : Arrays.asList(p.hand, p.drawPile, p.discardPile,p.exhaustPile, ExperimentCardManager.experiments)) {
                        for (AbstractCard q : cardGroup.group) {
                            if (q instanceof VanishingCard) {
                                AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
                                    @Override
                                    public void update() {
                                        isDone = true;
                                        q.untip();
                                        AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(q, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                                        cardGroup.removeCard(q);
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }

}
