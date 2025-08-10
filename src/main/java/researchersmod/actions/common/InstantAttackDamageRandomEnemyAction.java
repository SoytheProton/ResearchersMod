package researchersmod.actions.common;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class InstantAttackDamageRandomEnemyAction extends AbstractGameAction {
    private AbstractCard card;
    private int damage;
    private AbstractGameAction.AttackEffect effect;
    private AbstractMonster target;

    public InstantAttackDamageRandomEnemyAction(AbstractCard card, AbstractGameAction.AttackEffect effect, AbstractMonster target) {
        this.card = card;
        this.effect = effect;
        this.target = target;
        this.damage = card.damage;
    }

    public InstantAttackDamageRandomEnemyAction(AbstractCard card, AbstractGameAction.AttackEffect effect) {
        this(card, effect, AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng));
    }

    public InstantAttackDamageRandomEnemyAction(AbstractCard card) {
        this(card, AbstractGameAction.AttackEffect.NONE);
    }

    public void update() {
        if (this.target != null) {
            float tmp = (float) damage;
            for (AbstractPower p : target.powers)
                tmp = p.atDamageFinalReceive(tmp, this.card.damageTypeForTurn, this.card);
            damage = MathUtils.floor(tmp);
            addToTop(new DamageAction(this.target, new DamageInfo(AbstractDungeon.player, damage, this.card.damageTypeForTurn), this.effect));
        }
        this.isDone = true;
    }
}

