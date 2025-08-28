package researchersmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

@SuppressWarnings("unused")
public class AddCardToPilePatch {
    public interface AddToPileInterface {
        void onAddCard(CardGroup group, AbstractCard card);
    }

    @SpirePatch(clz = CardGroup.class, method = "addToHand")
    @SpirePatch(clz = CardGroup.class, method = "addToTop")
    @SpirePatch(clz = CardGroup.class, method = "addToBottom")
    @SpirePatch(clz = CardGroup.class, method = "addToRandomSpot")
    public static class onAddCardPatch {
        @SpirePrefixPatch
        public static void Prefix(CardGroup __instance, AbstractCard __c) {
            if(__c instanceof AddToPileInterface && (AbstractDungeon.currMapNode != null && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT)) ((AddToPileInterface) __c).onAddCard(__instance, __c);
        }
    }
}
