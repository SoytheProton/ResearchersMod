package researchersmod.powers.interfaces;

import com.megacrit.cardcrawl.powers.AbstractPower;

public class ExperimentInterfaces {
    public interface onExperimentInterface {
        void onExperiment(AbstractPower power);
    }

    public interface onCompletionInterface {
        void onCompletion(AbstractPower power);
    }

    public interface onTerminateInterface {
        void onTerminate(AbstractPower power);
    }

}

