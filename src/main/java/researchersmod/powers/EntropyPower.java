package researchersmod.powers;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import researchersmod.Researchers;
import researchersmod.cardmods.BetterEtherealMod;
import researchersmod.cardmods.PhaseMod;
import researchersmod.util.Wiz;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;

public class EntropyPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(EntropyPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;
    private boolean upgraded = false;
    public static CardGroup srcCommonCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
    public static CardGroup srcUncommonCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
    public static CardGroup srcRareCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);

    public EntropyPower(AbstractCreature owner, boolean upg) {
        super(POWER_ID, TYPE, TURNBASED, owner, 1);
        upgraded = upg;
        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        flash();
        AbstractCard card = returnTrulyRandomCardInCombat();
        CardModifierManager.addModifier(card,new BetterEtherealMod());
        if(upgraded)
            CardModifierManager.addModifier(card,new PhaseMod());
        Wiz.atb(new MakeTempCardInHandAction(card));
    }

    public static AbstractCard returnTrulyRandomCardInCombat() {
        ArrayList<AbstractCard> list = new ArrayList<>();
        for (AbstractCard c : srcCommonCardPool.group) {
            if (!c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
            }
        }
        for (AbstractCard c : srcUncommonCardPool.group) {
            if (!c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
            }
        }
        for (AbstractCard c : srcRareCardPool.group) {
            if (!c.hasTag(AbstractCard.CardTags.HEALING)) {
                list.add(c);
            }
        }
        return list.get(cardRandomRng.random(list.size() - 1));
    }

    public void updateDescription() {
        String plural = "s";
        if(this.amount == 1)
            plural = "";
        String upgrade = "";
        if(upgraded)
            upgrade = DESCRIPTIONS[1];
        this.description = String.format(DESCRIPTIONS[0],this.amount,plural,upgrade);
    }
}
