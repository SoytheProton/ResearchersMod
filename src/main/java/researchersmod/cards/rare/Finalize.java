package researchersmod.cards.rare;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.actions.CompletionAction;
import researchersmod.actions.TerminateAction;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.interfaces.ExperimentPower;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class Finalize extends BaseCard {
    public static final String ID = makeID(Finalize.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public Finalize() {
        super(ID, info);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int i = 0;
        for(AbstractPower po : p.powers) {
            if(po instanceof ExperimentPower) {
                if(upgraded) Wiz.atb(new CompletionAction(p, po));
                Wiz.atb(new TerminateAction(p, po));
                i++;
            }
        }
        Wiz.atb(new DrawCardAction(i));
        Wiz.atb(new GainEnergyAction(i));
    }
}
