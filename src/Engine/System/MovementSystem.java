package Engine.System;
import Engine.Components.TransformComponent;
import Engine.Core.ActiveEntities;
import Engine.Core.Component;
import Engine.Core.Entity;

/**
 * The {@code MovementSystem} updates the physical positions of entities based on
 * their velocity data.
 */
public class MovementSystem implements Component {

    /**
     * Constructs a new {@code MovementSystem}.
     */
    public MovementSystem() {

    }

    /**
     * Iterates through all active entities and updates their transform positions.
     *
     * @param DeltaTime The time elapsed since the last frame.
     */
    @Override
    public void update(double DeltaTime) {
        //loop to get transform of all entities
        for (Entity entities : ActiveEntities.getActiveEntities()) {
            TransformComponent trans = entities.getComponent(TransformComponent.class);
            if (trans == null) continue;

            trans.x += trans.velocityX * DeltaTime;
            trans.y += trans.velocityY * DeltaTime;
        }
    }
}