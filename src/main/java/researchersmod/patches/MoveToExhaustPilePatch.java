package researchersmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import researchersmod.util.Wiz;

import java.util.ArrayList;

public class MoveToExhaustPilePatch {
    public interface CardExhaustedInterface {
        void onCardExhausted(AbstractCard card);
    }

        @SpirePatch(clz = CardGroup.class, method = "moveToExhaustPile")
        public static class onCardExhaustedPatch {
            @SpirePrefixPatch
            public static void Prefix(CardGroup __instance, AbstractCard __c) {
                for(AbstractCard c : new ArrayList<AbstractCard>() {{
                    addAll(Wiz.p().hand.group);
                    addAll(Wiz.p().discardPile.group);
                    addAll(Wiz.p().drawPile.group);
                }}) {
                    if(c instanceof CardExhaustedInterface) {
                        ((CardExhaustedInterface) c).onCardExhausted(__c);
                    }
                }
            }
        }
}
