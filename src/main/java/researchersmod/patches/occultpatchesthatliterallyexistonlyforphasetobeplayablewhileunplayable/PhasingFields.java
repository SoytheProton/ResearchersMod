package researchersmod.patches.occultpatchesthatliterallyexistonlyforphasetobeplayablewhileunplayable;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(
        clz = AbstractCard.class,
        method = SpirePatch.CLASS
)
public class PhasingFields {
    public static SpireField<Boolean> isPhasing = new SpireField<>(()->false);
    public static SpireField<Boolean> notEnoughEnergy = new SpireField<>(()->false); //if you don't have enough energy for a card
    public static SpireField<Boolean> isOccultPlayable = new SpireField<>(()->false); //if a card normally isn't playable, but is playable due to occult
}
