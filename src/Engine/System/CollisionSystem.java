package Engine.System;
import Engine.Components.CollisionComponent;
import Engine.Components.TransformComponent;
import Engine.Core.Component;
import Engine.Core.Entity;
import Engine.Data.DataBase;
import Engine.Events.CollisionEvent;
import Engine.Events.EventBus;
import Engine.Data.EntityData;
import Engine.Math.SweptCollision;

import java.io.FileNotFoundException;
import java.util.List;

import static Engine.Core.ActiveEntities.getActiveEntities;

//the system that will run every collision check and logic
public class CollisionSystem implements Component {
    EventBus eventBus;

    public CollisionSystem(EventBus eventBus){
        this.eventBus = eventBus;
    }

    @Override
    public void update(double DeltaTime) {
        List<Entity> entities = getActiveEntities();

        //loop to find what it is being collided and what it can be collided with
        //why is this fucking confusing?
        for (int i = 0; i < getActiveEntities().size(); i++) {
            //get all of entityA's stuff
            Entity entityA = entities.get(i);

            //get the collision component from A
            //owner, pretty much
            CollisionComponent collA = entityA.getComponent(CollisionComponent.class);
            TransformComponent transformA = entityA.getComponent(TransformComponent.class); //get location

            //if both are null, skip them
            if (collA == null || transformA == null) {
                continue;
            }

            //loop to find entity B
            for (int j = 0; j < getActiveEntities().size(); j++) {

                if (i == j) { continue; }

                //get the collision component from B
                //other, pretty much
                Entity entityB = entities.get(j);
                CollisionComponent collB = entityB.getComponent(CollisionComponent.class);

                //check if both are null
                if (collB == null ) {
                    continue;
                }

                //make a boolean that determines if entities can
                //do collision
                boolean canHit = false;

                //call for the database
                EntityData checkA = DataBase.getTemplate(collA.type);

                //somehow, check if both entities
                //are registered to collide with each other
                if (checkA != null && checkA.collideList != null) {

                    for (String targetType : checkA.collideList) {

                        if (targetType.equals(collB.type)) {
                                canHit = true;
                                break;
                        }
                    }
                }

                //if canHit is true, run all the collision math
                if (canHit) {
                    SweptCollision result = SweptCollision.boxCollision(transformA, collA, collB, DeltaTime);

                    if (result.time_ < 1.0) {
                        eventBus.publishEvent(new CollisionEvent(entityA, entityB, result.normalX_, result.normalY_, result.time_, DeltaTime));
                    }
                }
            } //end of j loop
        } //end of i loop
    }
}
