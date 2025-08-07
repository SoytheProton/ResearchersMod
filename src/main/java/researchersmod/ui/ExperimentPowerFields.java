package researchersmod.ui;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.AbstractPower;

@SpirePatch(
        clz = AbstractPower.class,
        method = SpirePatch.CLASS
)
public class ExperimentPowerFields {
    public static SpireField<Boolean> instantImmunity = new SpireField<>(() -> false); // Makes a card immune to hyperfocus and other instant Terminate (Complete) effects.
    public static SpireField<Boolean> freeToTerminateOnce = new SpireField<>(() -> false); // Used for Moth Bookmark
    public static SpireField<AbstractCard> attachedCard = new SpireField<>(() -> null); // Used to designated attached cards for Experiment Cards.
    public static SpireField<AbstractPower> attachedPower = new SpireField<>(() -> null); // Used to designated attached power for Experiment Attachments.
}
