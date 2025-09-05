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
import researchersmod.cards.ExperimentCard;
import researchersmod.util.PowerIDWrapper;
import researchersmod.util.Wiz;

import java.util.ArrayList;

@SpirePatch2(
        clz = UseCardAction.class,
        method = SpirePatch.CONSTRUCTOR,
        paramtypez = { AbstractCard.class, AbstractCreature.class }
)
public class PowerIDPatch {
    public static void Prefix(AbstractCard card) {
        if(card.type == AbstractCard.CardType.ATTACK && card instanceof ExperimentCard) {
            for (AbstractPower p : Wiz.p().powers) {
                if (Researchers.statSnapshotting.containsKey(p.ID)) {
                    PowerIDWrapper wrapper = Researchers.statSnapshotting.get(p.ID);
                    float amt = wrapper.getUseAmount() ? p.amount : wrapper.getNumber();
                    if (wrapper.getIsMult() && wrapper.getUseAmount()) amt *= wrapper.getNumber();
                    CardModifierManager.addModifier(card, new DamageModMod(amt, wrapper.getIsMult()));
                }
            }
            ArrayList<AbstractCardModifier> modList = new ArrayList<>();
            for (AbstractCardModifier mod : CardModifierPatches.CardModifierFields.cardModifiers.get(card)) {
                if (Researchers.statSnapshotting.containsKey(mod.identifier(card))) {
                    PowerIDWrapper wrapper = Researchers.statSnapshotting.get(mod.identifier(card));
                    modList.add(new DamageModMod(wrapper.getNumber(), wrapper.getIsMult()));
                }
            }
            for (AbstractCardModifier mod : modList) {
                CardModifierManager.addModifier(card, mod);
            }
        }
    }
}
