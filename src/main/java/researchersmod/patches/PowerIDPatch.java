package researchersmod.patches;


import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CardModifierPatches;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.cardmods.DamageModMod;
import researchersmod.util.PowerIDWrapper;
import researchersmod.util.Wiz;

@SpirePatch2(
        clz = UseCardAction.class,
        method = SpirePatch.CONSTRUCTOR,
        paramtypez = { AbstractCard.class, AbstractCreature.class }
)
public class PowerIDPatch {
    public static void Prefix(AbstractCard card) {
        for(AbstractPower p : Wiz.p().powers) {
            if(Researchers.statSnapshotting.containsKey(p.ID)) {
                PowerIDWrapper wrapper = Researchers.statSnapshotting.get(p.ID);
                float amt = wrapper.getUseAmount() ? p.amount : wrapper.getNumber();
                if(wrapper.getIsMult() && wrapper.getUseAmount()) amt *= wrapper.getNumber();
                CardModifierManager.addModifier(card, new DamageModMod(amt,wrapper.getIsMult()));
            }
        }
        for(AbstractCardModifier mod : CardModifierPatches.CardModifierFields.cardModifiers.get(card)) {
            if(Researchers.statSnapshotting.containsKey(mod.identifier(card))) {
                PowerIDWrapper wrapper = Researchers.statSnapshotting.get(mod.identifier(card));
                CardModifierManager.addModifier(card, new DamageModMod(wrapper.getNumber(),wrapper.getIsMult()));
            }
        }
    }

    private static AbstractCard makeNewCard(AbstractCard card, boolean sameUUID) {
        if(sameUUID) return card.makeSameInstanceOf();
        return card.makeStatEquivalentCopy();
    }
}
