package Engine.Profiles;

public class BaseProfile {

    public double startUpDuration;
    public double activeDuration;
    public double recoveryDuration;

    public BaseProfile(double startUpDuration, double activeDuration, double recoveryDuration) {

        this.startUpDuration = startUpDuration;
        this.activeDuration = activeDuration;
        this.recoveryDuration = recoveryDuration;
    }
}

