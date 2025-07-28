package researchersmod.cards.uncommon;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.Researchers;
import researchersmod.cards.ExperimentCard;
import researchersmod.cards.status.PlasmicEnergy;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.experiments.PlasmaPulseExperiment;
import researchersmod.util.CardStats;

import java.util.ArrayList;
import java.util.List;

public class PlasmaPulse extends ExperimentCard {
    public static final String ID = makeID(PlasmaPulse.class.getSimpleName());
    private ArrayList<TooltipInfo> ToolTip;
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    public PlasmaPulse() {
        super(ID, info,1);
        setDamage(8,3);
        cardsToPreview = new PlasmicEnergy();
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        if(ToolTip == null)
        {
            ToolTip = new ArrayList<>();
            ToolTip.add(new TooltipInfo(Researchers.keywords.get("Etheric").PROPER_NAME, Researchers.keywords.get("Etheric").DESCRIPTION));
        }
        return ToolTip;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new ApplyPowerAction(p, p, new PlasmaPulseExperiment(p, this.Trial, this)));
    }
}
