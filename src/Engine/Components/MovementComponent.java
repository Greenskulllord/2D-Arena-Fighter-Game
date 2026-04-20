package Engine.Components;

import Engine.Core.Component;

public class MovementComponent implements Component {
    double speed;

    public MovementComponent(double speed) {
        this.speed = speed;

    }
    @Override
    public void update(double DeltaTime) {

    }
}
