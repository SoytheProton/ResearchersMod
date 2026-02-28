package researchersmod.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.DistortionPower;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class ParticleAccelerator extends BaseCard {
    public static final String ID = makeID(ParticleAccelerator.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    public ParticleAccelerator() {
        super(ID, info);
        setDamage(4,2);
        setMagic(1);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int i = 0;
        for(AbstractCard c : Wiz.p().hand.group) {
            if(c.type == CardType.STATUS)
                i++;
        }
        String plural = cardStrings.EXTENDED_DESCRIPTION[1];
        if(i == 1)
            plural = cardStrings.EXTENDED_DESCRIPTION[2];
        this.rawDescription = cardStrings.DESCRIPTION + String.format(cardStrings.EXTENDED_DESCRIPTION[0],i,plural);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new AbstractGameAction() {
            @Override
            public void update() {
                for(AbstractCard c : Wiz.p().hand.group) {
                    if(c.type == CardType.STATUS) {
                        Wiz.att(new ApplyPowerAction(m, p, new DistortionPower(m, 1)));
                        Wiz.att(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL)));
                    }
                }
                this.isDone = true;
            }
        });
    }
}
