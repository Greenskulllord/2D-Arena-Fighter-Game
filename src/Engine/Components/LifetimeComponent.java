package Engine.Components;
import Engine.Core.Component;
import Engine.Core.Entity;
import java.io.FileNotFoundException;

/**
 * The {@code LifetimeComponent} tracks how much time it has remaining before expiration.
 */
public class LifetimeComponent implements Component {
    /** The starting time or duration for the entity's life. */
    public double startTimer;

    /**
     * Constructs a {@code LifetimeComponent} with a defined starting time.
     *
     * @param timeStart The initial value for the lifetime timer.
     */
    public LifetimeComponent(double timeStart) {
        this.startTimer = timeStart;
    }

    @Override
    public void update(double DeltaTime) {
        //empty because it's just a data component
    }
}