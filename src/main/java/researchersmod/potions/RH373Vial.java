package researchersmod.potions;

import basemod.helpers.CardPowerTip;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import researchersmod.actions.common.ManualExperimentAction;
import researchersmod.cards.colorless.Singularity;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.Wiz;

import static researchersmod.Researchers.makeID;

public class RH373Vial extends BasePotion {
    public static final String ID = makeID(RH373Vial.class.getSimpleName());
    public RH373Vial() {
        super(ID,1,PotionRarity.RARE, PotionSize.T,Color.DARK_GRAY.cpy(),null,null);
        playerClass = ResearchersCharacter.Meta.RESEARCHERS;
        labOutlineColor = characterColor.cpy();
    }

    @Override
    public String getDescription() {
        String plural = DESCRIPTIONS[2];
        if(potency == 1) plural = DESCRIPTIONS[1];
        return String.format(DESCRIPTIONS[0],potency,plural);
    }

    @Override
    public void use(AbstractCreature creature) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    AbstractCard tmp = new Singularity();
                    tmp.dontTriggerOnUseCard = true;
                    tmp.applyPowers();
                    tmp.use(Wiz.adp(),(AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng));
                    Wiz.atb(new ManualExperimentAction(tmp));
                    this.isDone = true;
                }
            });
        }
    }

    @Override
    public void addAdditionalTips() {
        this.tips.add(new CardPowerTip(new Singularity()));
    }
}
