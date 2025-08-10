package researchersmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.ExperimentCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.experiments.CutThroughLightExperiment;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class CutThroughLight extends ExperimentCard {
    public static final String ID = makeID(CutThroughLight.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );

    public CutThroughLight() {
        super(ID, info,2,1);
        setDamage(12);
        setMagic(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        Wiz.applyToSelf(new CutThroughLightExperiment(p,this.trial,this));
    }
}
