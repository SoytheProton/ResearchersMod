package researchersmod.ui;

import basemod.EasyConfigPanel;
import researchersmod.Researchers;

public class ModConfig extends EasyConfigPanel {
    public static boolean enablePhaseNumbers = true;
    public static boolean enableUnlocks = true;
    public ModConfig() {
        super(Researchers.modID, Researchers.makeID("ModConfig"));
    }
}
