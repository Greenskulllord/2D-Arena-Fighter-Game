package Engine.Components;

import Engine.Core.Component;

/**
 * The {@code MovementComponent} defines the speed and mobility capabilities of an entity.
 */
public class MovementComponent implements Component {
    double speed;

    /**
     * Constructs a {@code MovementComponent} with a specific movement speed.
     *
     * @param speed The velocity magnitude at which the entity moves.
     */
    public MovementComponent(double speed) {
        this.speed = speed;

    }
    @Override
    public void update(double DeltaTime) {

    }
}