package researchersmod.cards.colorless;

import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cardmods.DoubleDamageOnce;
import researchersmod.cards.BaseCard;
import researchersmod.util.CardStats;

@NoCompendium
public class markettester extends BaseCard {
    public static final String ID = makeID(markettester.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.SKILL,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            0
    );
    public markettester() {
        super(ID, info);
        setMagic(1,1);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(magicNumber,cardStrings.EXTENDED_DESCRIPTION[0],false,false,(c -> true),(cards) -> {
            for (AbstractCard c : cards) {
                CardModifierManager.addModifier(c, new DoubleDamageOnce());
            }
        }));
    }
}

