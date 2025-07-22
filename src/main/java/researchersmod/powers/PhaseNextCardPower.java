package researchersmod.powers;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import researchersmod.Researchers;
import researchersmod.cardmods.PhaseMod;
import researchersmod.cards.ExperimentCard;
import researchersmod.patches.occultpatchesthatliterallyexistonlyforphasetobeplayablewhileunplayable.PhasingFields;

import java.util.UUID;

public class PhaseNextCardPower extends BasePower {
    public static final String POWER_ID = Researchers.makeID(PhaseNextCardPower.class.getSimpleName());
    public static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURNBASED = false;

    public PhaseNextCardPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURNBASED, owner, amount);
        updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.SKILL || card.type == AbstractCard.CardType.ATTACK) {
            flash();
            addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    AbstractCard tmp = card.makeSameInstanceOf();
                    tmp.glowColor = Color.BLUE;
                    tmp.beginGlowing();
                    tmp.transparency = 0.6F;
                    tmp.targetTransparency = 0.6F;
                    tmp.current_y = -200.0F * Settings.scale;
                    tmp.target_x = Settings.WIDTH / 2.0F;
                    tmp.target_y = Settings.HEIGHT / 2.0F;
                    tmp.targetAngle = 0.0F;
                    tmp.purgeOnUse = true;
                    if(tmp instanceof ExperimentCard)
                        tmp.uuid = UUID.randomUUID();
                    PhasingFields.isPhasing.set(tmp,true);
                    tmp.applyPowers();
                    addToTop((new NewQueueCardAction(tmp, (AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), false, true)));
                    for (AbstractPower p : AbstractDungeon.player.powers)
                        if (p instanceof PhaseMod.OnPhaseInterface)
                            ((PhaseMod.OnPhaseInterface) p).onPhase(card);
                    Researchers.cardsPhasedThisTurn++;
                    Researchers.cardsPhasedThisCombat++;
                    this.isDone = true;
                }
            });
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    public void updateDescription() {
        String plural = "s";
        if(this.amount == 1)
            plural = "";
        String plural2 = "are";
        if(this.amount == 1) plural2 = "is";
        this.description = String.format(DESCRIPTIONS[0],this.amount,plural,plural,plural2);
    }
}
