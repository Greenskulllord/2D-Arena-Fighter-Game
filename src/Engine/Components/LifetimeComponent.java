package Engine.Components;
import Engine.Core.Component;
import Engine.Core.Entity;
import java.io.FileNotFoundException;

public class LifetimeComponent implements Component {
    int startTimer;
    Entity owner;

    LifetimeComponent(int timeStart, Entity owner) {
        this.startTimer = timeStart;
        this.owner = owner;

    }

    @Override
    public void update(double DeltaTime) throws FileNotFoundException {

    }
}
