package researchersmod.powers.interfaces;

import com.megacrit.cardcrawl.powers.AbstractPower;

public class ExperimentInterfaces {
    public interface OnExperimentInterface {
        void onExperiment(AbstractPower power);
    }

    public interface OnCompletionInterface {
        void onCompletion(AbstractPower power);
    }

    public interface OnAnyCompletionInterface {
        void onCompletion(AbstractPower power);
    }

    public interface OnTerminateInterface {
        void onTerminate(AbstractPower power);
    }
}

