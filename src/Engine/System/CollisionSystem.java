package Engine.System;
import Engine.Components.CollisionComponent;
import Engine.Components.TransformComponent;
import Engine.Core.Component;
import Engine.Core.Entity;
import Engine.Data.DataBase;
import Engine.Math.BoxCollision;
import Engine.Data.EntityData;
import java.io.FileNotFoundException;
import java.util.List;

import static Engine.Core.ActiveEntities.getActiveEntities;

//the system that will run every collision check and logic
public class CollisionSystem implements Component {

    @Override
    public void update(double DeltaTime) throws FileNotFoundException {
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

            //figure out how it would want to move
            double finalMoveX = transformA.velocityX * DeltaTime;
            double finalMoveY = transformA.velocityY * DeltaTime;

            //loop to find entity B
            for (int j = 0; j < getActiveEntities().size(); j++) {

                if (i == j ) { continue;}

                //put B entities into their own variables
                Entity entityB = entities.get(j);

                //get the collision component from B
                //other, pretty much
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

                // somehow, check if both entities
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

                    BoxCollision result = BoxCollision.BoxCollisionMath(transformA, collA, collB, DeltaTime);
                    //run the actual collision logic
                    if (DeltaTime > 0) {
                        transformA.velocityX = result.totalMoveX / DeltaTime;
                        transformA.velocityY = result.totalMoveY / DeltaTime;
                    }

                    finalMoveX = result.totalMoveX;
                    finalMoveY = result.totalMoveY;

                   }

                } //end of j loop

            transformA.x += finalMoveX;
            transformA.y += finalMoveY;

        } //end of i loop
    }
}
