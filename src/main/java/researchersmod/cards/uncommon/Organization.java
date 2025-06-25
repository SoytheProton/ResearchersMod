package researchersmod.cards.uncommon;

import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import researchersmod.cardmods.ExhaustiveMod;
import researchersmod.cardmods.PhaseMod;
import researchersmod.cards.BaseCard;
import researchersmod.cards.targeting.CardTargeting;
import researchersmod.character.ResearchersCharacter;
import researchersmod.powers.experiments.OrganizationExpAttachment;
import researchersmod.ui.ExperimentCardManager;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class Organization extends BaseCard {
    public static final String ID = makeID(Organization.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTargeting.EXPERIMENT,
            0
    );
    public Organization() {
        super(ID, info);
        setMagic(2);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard target = CardTargeting.getTarget(this);
        if(target != null) {
            target.superFlash();
            Wiz.atb(new ApplyPowerAction(p, p, new OrganizationExpAttachment(p,target,upgraded)));
        }

    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (ExperimentCardManager.experiments.group.isEmpty()) {
            canUse = false;
            this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        }
        if (!ExperimentCardManager.experiments.group.isEmpty()) {
            canUse = true;
        }
        return canUse;
    }
}

