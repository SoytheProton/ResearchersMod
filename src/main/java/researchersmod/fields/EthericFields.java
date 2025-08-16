package researchersmod.fields;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(
        clz = AbstractCard.class,
        method = SpirePatch.CLASS
)
public class EthericFields {
    public static SpireField<Integer> etheric = new SpireField<>(()->-1);
    public static SpireField<Integer> baseEtheric = new SpireField<>(()->-1);
    public static SpireField<Boolean> isEthericUpgraded = new SpireField<>(()->false);
}

