package researchersmod.cards.uncommon;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import researchersmod.cardmods.PhaseMod;
import researchersmod.cards.BaseCard;
import researchersmod.character.ResearchersCharacter;
import researchersmod.util.CardStats;
import researchersmod.util.Wiz;

public class AppliedInformation extends BaseCard {
    public static final String ID = makeID(AppliedInformation.class.getSimpleName());
    private static final CardStats info = new CardStats(
            ResearchersCharacter.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public AppliedInformation() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new SelectCardsInHandAction(1,cardStrings.EXTENDED_DESCRIPTION[0],false,false,(c->true),(cards -> {
          for(AbstractCard c : cards) {
              AbstractCard tmp = c.makeStatEquivalentCopy();
              if(CardModifierManager.hasModifier(tmp, PhaseMod.ID)) {
                  AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(tmp));
                  Wiz.atb(new ExhaustSpecificCardAction(tmp,p.hand,true));
                  if(!upgraded) Wiz.atb(new ExhaustSpecificCardAction(c,p.hand,true));
                  else {
                      AbstractCard tmp2 = c.makeStatEquivalentCopy();
                      p.limbo.addToBottom(tmp2);
                      Wiz.atb(new ExhaustSpecificCardAction(tmp2,p.limbo,true));
                  }
              } else {
                  Wiz.atb(new MakeTempCardInHandAction(tmp));
              }
          }
        })));
    }
}


