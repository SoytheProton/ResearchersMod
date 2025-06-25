package researchersmod.util;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

public class ExpDescriptionHelper {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("researchersmod:ExpDescriptionHelper");

    public static int countStr(String str, String substr) {
        int count = 0;
        int lastIndex = 0;
        while (lastIndex != -1) {
            lastIndex = str.indexOf(substr,lastIndex);
            if (lastIndex != -1) {
                count++;
                lastIndex += substr.length();
            }
        }
        return count;
    }

    public static String completionEffect(String rawDescription, String addedEffect) {
        String returnString = rawDescription;
        int colonInstance = countStr(rawDescription, ": ");
        boolean terminateInstance = rawDescription.contains("Terminate:");
        if (terminateInstance && colonInstance == 2) { // Completion: and Terminate: effect
            int index = rawDescription.indexOf(": ",rawDescription.indexOf("Experiment"))+1;
            String[] description = {rawDescription.substring(0,index),rawDescription.substring(index)};
            description[0] = description[0] + " " + addedEffect;
            returnString = description[0] + description[1];
        } else if (terminateInstance && colonInstance == 1) { //Terminate: effect but no Completion
            int index = rawDescription.indexOf("{researchersmod}:Terminate: ") - 2;
            String[] description = {rawDescription.substring(0,index),rawDescription.substring(index +1)};
            description[0] = description[0] + ": " + addedEffect;
            returnString = description[0] + description[1];
        } else if (colonInstance <= 1) { // Completion or no effect
            returnString = rawDescription + " " + addedEffect;
        } return returnString;
    }

    public static String terminationEffect(String rawDescription, String addedEffect) {
        String returnString = rawDescription;
        boolean terminateInstance = rawDescription.contains("Terminate:");
        if(terminateInstance)
            returnString = rawDescription + " " + addedEffect;
        else
            returnString = rawDescription + " " + uiStrings.TEXT[1] + addedEffect;
        return returnString;
    }
}
