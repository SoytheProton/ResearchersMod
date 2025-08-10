package researchersmod.ui;

import basemod.EasyConfigPanel;
import researchersmod.Researchers;

public class ModConfig extends EasyConfigPanel {
    public static boolean enablePhaseNumbers = true;
    public static boolean disableRelics = false;
    public static boolean enableUnlocks = true;
    public static boolean altBehaviorAdjustment = false;
    public static boolean noConditionThermiteBlade = false;
    public static boolean emergencyLogging = false;
    public static boolean etherealOverrideRetain = true;
    public static boolean oldVolcanicResearch = false;
    public ModConfig() {
        super(Researchers.modID, Researchers.makeID("ModConfig"));
    }
}
