package researchersmod.cards.uncommon;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.actions.common.AddPhaseModAction;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

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
        setMagic(1,1);
        // this card was originally called "Ink Blot" but I thought that was lame.
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(magicNumber,cardStrings.EXTENDED_DESCRIPTION[0],false,false,(c -> true),(cards) -> {
            for (AbstractCard c : cards) {
                Wiz.att(new AddPhaseModAction(c,true));
            }
        }));
    }
}

