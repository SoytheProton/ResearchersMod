package researchersmod.cards.status;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.actions.SulfurPodAction;
import researchersmod.cards.BaseCard;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class SulfurPod extends BaseCard {
    public static final String ID = makeID(SulfurPod.class.getSimpleName());
    private static final CardStats info = new CardStats(
            CardColor.COLORLESS,
            CardType.STATUS,
            CardRarity.SPECIAL,
            CardTarget.ALL_ENEMY,
            1
    );
    public SulfurPod() {
        super(ID, info);
        this.exhaust = true;
        this.selfRetain = true;
        setMagic(3, 1);
        setPhase(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int[] damage = new int[AbstractDungeon.getMonsters().monsters.size()];
        for(int i = damage.length; i > 0; i--) {
            damage[i-1] = magicNumber;
        }
        addToBot(new SFXAction("ATTACK_POISON"));
        addToBot(new DamageAllEnemiesAction(p, damage, DamageInfo.DamageType.HP_LOSS, AbstractGameAction.AttackEffect.POISON));
        Wiz.atb(new SulfurPodAction());
    }
}