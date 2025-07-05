package researchersmod.cards.uncommon;

import com.evacipated.cardcrawl.mod.stslib.actions.common.DamageCallbackAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cards.BaseCard;
import researchersmod.cards.status.SulfurPod;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class SulfuricOxidation extends BaseCard {
    public static final String ID = makeID(SulfuricOxidation.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );


    public SulfuricOxidation() {
        super(ID, info);
        setDamage(3);
        setMagic(3,1);
        this.cardsToPreview = new SulfurPod();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = magicNumber; i > 0; i--) {
            addToBot(new DamageCallbackAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.POISON,(damageDealt) -> {
                if(damageDealt > 0) {
                    Wiz.atb(new MakeTempCardInHandAction(new SulfurPod()));
                }
            }));
        }
    }
}
