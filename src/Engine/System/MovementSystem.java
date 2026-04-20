package Engine.System;
import Engine.Components.TransformComponent;
import Engine.Core.ActiveEntities;
import Engine.Core.Component;
import Engine.Core.Entity;

public class MovementSystem implements Component {

    public MovementSystem() {

    }

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