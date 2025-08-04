package researchersmod.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import researchersmod.Researchers;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.PhaseNextCardPower;
import researchersmod.util.Wiz;

import static researchersmod.Researchers.makeID;

public class Coffee extends BasePotion {
    public static final String ID = makeID(Coffee.class.getSimpleName());
    public Coffee() {
        super(ID,1,PotionRarity.UNCOMMON, PotionSize.H,Color.BROWN.cpy(),null,null);
        playerClass = ResearchersCharacter.Meta.RESEARCHERS;
        labOutlineColor = characterColor.cpy();
    }

    @Override
    public String getDescription() {
        if(potency == 1) return DESCRIPTIONS[0];
        return String.format(DESCRIPTIONS[1],potency);
    }

    @Override
    public void use(AbstractCreature creature) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractPlayer p = Wiz.adp();
            Wiz.atb(new ApplyPowerAction(p, p, new PhaseNextCardPower(p,potency)));
        }
    }

    @Override
    public void addAdditionalTips() {
        this.tips.add(new PowerTip(Researchers.keywords.get("Phase").PROPER_NAME, Researchers.keywords.get("Phase").DESCRIPTION));
    }
}
