package researchersmod.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import researchersmod.Researchers;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.DistortionPower;
import researchersmod.util.Wiz;

import static researchersmod.Researchers.makeID;

public class LiquidData extends BasePotion {
    public static final String ID = makeID(LiquidData.class.getSimpleName());
    public LiquidData() {
        super(ID,2,PotionRarity.COMMON, PotionSize.S,Color.PINK.cpy(),null,Color.PURPLE.cpy());
        playerClass = ResearchersCharacter.Meta.RESEARCHERS;
        labOutlineColor = characterColor.cpy();
        this.isThrown = true;
        this.targetRequired = true;
    }

    @Override
    public String getDescription() {
        return String.format(DESCRIPTIONS[0],potency);
    }

    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            Wiz.atb(new ApplyPowerAction(target, target, new DistortionPower(target,potency)));
        }
    }

    @Override
    public void addAdditionalTips() {
        this.tips.add(new PowerTip(Researchers.keywords.get("Distortion").PROPER_NAME, Researchers.keywords.get("Distortion").DESCRIPTION));
    }
}
