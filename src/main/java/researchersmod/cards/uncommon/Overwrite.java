package researchersmod.cards.uncommon;

import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cardmods.ExhaustiveMod;
import researchersmod.cardmods.PhaseMod;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;
import com.evacipated.cardcrawl.mod.stslib.variables.ExhaustiveVariable;

public class Overwrite extends BaseCard {
    public static final String ID = makeID(Overwrite.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );
    public Overwrite() {
        super(ID, info);
        setMagic(1);
        // this card was originally called "Ink Blot" but I thought that was lame.
        setExhaust(true,false);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(magicNumber,cardStrings.EXTENDED_DESCRIPTION[0],false,false,(c -> true),(cards) -> {
            for (AbstractCard c : cards) {
                c.flash(Color.BLUE.cpy());
                CardModifierManager.addModifier(c,new PhaseMod());
            }
        }));
    }


    @Override
    public void upgrade() {
        if(!this.upgraded) {
            super.upgrade();
            this.exhaust = false; // just to make sure
            // ExhaustiveVariable.setBaseValue(this,2);
            ExhaustiveMod mod = new ExhaustiveMod();
            mod.editExhaustive(2);
            CardModifierManager.addModifier(this, mod);
        }

    }
}

