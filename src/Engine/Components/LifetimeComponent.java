package Engine.Components;
import Engine.Core.Component;
import Engine.Core.Entity;
import java.io.FileNotFoundException;

public class LifetimeComponent implements Component {
    public double startTimer;


    public LifetimeComponent(double timeStart) {
        this.startTimer = timeStart;
    }

    @Override
    public void update(double DeltaTime) throws FileNotFoundException {

    }
}
