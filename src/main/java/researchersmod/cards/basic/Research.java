package researchersmod.cards.basic;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import researchersmod.cards.BaseCard;
import researchersmod.cards.optioncards.gaindexterity;
import researchersmod.cards.optioncards.freepowers;
import researchersmod.cards.optioncards.gainstrength;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;

import java.util.ArrayList;

public class Research extends BaseCard {
    public static final String ID = makeID(Research.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            AbstractCard.CardType.POWER,
            AbstractCard.CardRarity.BASIC,
            AbstractCard.CardTarget.SELF,
            0
    );

    private static final int MAGIC = 1;

    public Research() {
        super(ID, info);

        setMagic(MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded) {
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
        } else {
            ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
            stanceChoices.add(new gainstrength());
            stanceChoices.add(new gaindexterity());
            addToBot(new ChooseOneAction(stanceChoices));
        }
    }
}