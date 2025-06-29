package researchersmod.powers;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.cardmods.BetterEtherealMod;
import researchersmod.cardmods.PhaseMod;
import researchersmod.util.Wiz;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;

public class EnthalpyPower extends BasePower implements NonStackablePower {
    public static final String POWER_ID = Researchers.makeID(EnthalpyPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;
    private boolean upgraded = false;
    public static CardGroup srcCommonCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
    public static CardGroup srcUncommonCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
    public static CardGroup srcRareCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);

    public EnthalpyPower(AbstractCreature owner, boolean upg, AbstractCard card) {
        super(POWER_ID, TYPE, TURNBASED, owner, 1);
        upgraded = upg;
        k = card;
        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        AbstractCard card = k.makeStatEquivalentCopy();
        CardModifierManager.addModifier(card,new BetterEtherealMod());
        if(!upgraded)
            CardModifierManager.removeModifiersById(card,PhaseMod.ID,true);
        Wiz.atb(new MakeTempCardInHandAction(card));
    }

    public void updateDescription() {
        String card = k.name;
        String upgrade = "";
        if(!upgraded)
            upgrade = DESCRIPTIONS[1];
        this.description = String.format(DESCRIPTIONS[0],card,upgrade);
    }
}
