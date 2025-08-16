package researchersmod.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import researchersmod.Researchers;
import researchersmod.fields.EthericFields;

public class EthericVariable extends DynamicVariable {
    @Override
    public String key() {
        return Researchers.makeID("eth");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return EthericFields.baseEtheric.get(card).intValue() != EthericFields.etheric.get(card).intValue();
    }

    @Override
    public int value(AbstractCard c) {
        return EthericFields.etheric.get(c);
    }

    @Override
    public int baseValue(AbstractCard c) {
        return EthericFields.baseEtheric.get(c);
    }

    @Override
    public boolean upgraded(AbstractCard c) {
        return EthericFields.isEthericUpgraded.get(c);
    }

    public static void setBaseValue(AbstractCard card, int amount)
    {
        EthericFields.baseEtheric.set(card, amount);
        EthericFields.etheric.set(card, amount);
        card.initializeDescription();
    }

    public static void upgrade(AbstractCard card, int amount)
    {
        EthericFields.isEthericUpgraded.set(card, true);
        setBaseValue(card, EthericFields.baseEtheric.get(card) + amount);
    }

    public static void increment(AbstractCard card)
    {
        EthericFields.etheric.set(card, EthericFields.etheric.get(card) - 1);
        if (EthericFields.etheric.get(card) <= 0) {
            card.isEthereal = true;
        }
        card.initializeDescription();
    }
}
