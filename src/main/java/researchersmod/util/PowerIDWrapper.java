package researchersmod.util;

public class PowerIDWrapper {
    private final float number;
    private final boolean isMult;
    private final boolean useAmount;
    public PowerIDWrapper(float number, boolean isMult, boolean useAmount) {
        this.number = number;
        this.isMult = isMult;
        this.useAmount = useAmount;
    }

    public PowerIDWrapper(float number, boolean isMult) {
        this.number = number;
        this.isMult = isMult;
        this.useAmount = false;
    }

    public PowerIDWrapper(boolean useAmount) {
        this.number = 0;
        this.isMult = false;
        this.useAmount = useAmount;
    }


    public float getNumber() { return number; }
    public boolean getIsMult() { return isMult; }
    public boolean getUseAmount() { return useAmount; }

}
