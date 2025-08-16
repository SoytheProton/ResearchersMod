package researchersmod.fields;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(
        clz = AbstractCard.class,
        method = SpirePatch.CLASS
)
public class ExperimentFields {
    public static SpireField<Boolean> purgingExperiment = new SpireField<>(()->false); // Experiments that are purging (Not Stated)
    public static SpireField<Boolean> exhaustingExperiment = new SpireField<>(()->false); // Experiments that are exhausting (Not Stated)
    public static SpireField<Boolean> playExperiment = new SpireField<>(()->false); // Experiments that are exhausting (Not Stated)
}

