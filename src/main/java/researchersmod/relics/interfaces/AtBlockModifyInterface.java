package researchersmod.relics.interfaces;

import com.megacrit.cardcrawl.cards.AbstractCard;

public interface AtBlockModifyInterface {
    default float atBlockModify(float block, AbstractCard card) {
        return block;
    }
}
