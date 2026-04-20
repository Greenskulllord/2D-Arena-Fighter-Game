package Engine.System;
import Engine.Components.TransformComponent;
import Engine.Events.CollisionEvent;
import Engine.Events.EventBus;

public class MovementConst {

    public MovementConst(EventBus event) {
        event.subscribeEvent(CollisionEvent.class, this::stopMovement);

    }

    public void stopMovement(CollisionEvent event) {
        //get the transform
        TransformComponent transform = event.A.getComponent(TransformComponent.class);
        if (transform == null) return;

        double frameVelocityX = transform.velocityX * event.DeltaTime;
        double frameVelocityY = transform.velocityY * event.DeltaTime;

        //calculate the vectors
        double vectorX = frameVelocityX * event.time;
        double vectorY = frameVelocityY * event.time;

        //vector 'a' is the remaining velocity
        //vector 'b' is the normals

        //calculate remaining velocity
        //this is vector 'a'
        //the normals are vector 'b'
        double remainVelX = frameVelocityX * (1.0 - event.time);
        double remainVelY = frameVelocityY * (1.0 - event.time);

        //calculate dot production
        double dotProduction = remainVelX *  event.normalX + remainVelY *  event.normalY;

        //calculate projection
        double projectionX = (dotProduction / ( event.normalX *  event.normalX +  event.normalY *  event.normalY) ) *  event.normalX;
        double projectionY = (dotProduction / ( event.normalX *  event.normalX +  event.normalY *  event.normalY) ) *  event.normalY;

        //I don't even fucking know what's what anymore
        //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
        //but this is just creating the 'into the wall' force
        double slideX = remainVelX - projectionX;
        double slideY = remainVelY - projectionY;

        if (event.DeltaTime > 0) {
            transform.velocityX = (vectorX + slideX) / event.DeltaTime;
            transform.velocityY = (vectorY + slideY) / event.DeltaTime;
        }
    }
}
