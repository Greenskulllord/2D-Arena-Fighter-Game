package Engine.Math;

import Engine.Components.CollisionComponent;
import Engine.Components.TransformComponent;

//this holds the math to handle what to do when collision happens
public class BoxCollision {

    //call components
    TransformComponent transform;
    CollisionComponent owner;
    CollisionComponent other;

    //declare variables
    double DeltaTime;
    public double totalMoveX;
    public double totalMoveY;
    public boolean isColliding;
    public double normalX;
    public double normalY;

    BoxCollision(double TotalX, double TotalY, double DeltaTime, boolean isColliding) {
        this.totalMoveX = TotalX;
        this.totalMoveY = TotalY;
        this.DeltaTime = DeltaTime;
        this.isColliding = isColliding;
    }

   public static BoxCollision BoxCollisionMath(TransformComponent transform, CollisionComponent owner,
                                               CollisionComponent other, double DeltaTime) {

        //call on swept collision to get the data
        SweptCollision sweptCollision = SweptCollision.boxBoxCollision(transform, owner, other, DeltaTime);

        //call on data to get values
        double normalX = sweptCollision.normalX_;
        double normalY = sweptCollision.normalY_;
        double time = sweptCollision.time_;

        //get frame velocity X and Y from swept collision
        double frameVelocityX = transform.velocityX * DeltaTime;
        double frameVelocityY = transform.velocityY * DeltaTime;

        if (time >= 1.0) {
            return new BoxCollision(frameVelocityX, frameVelocityY, DeltaTime, false);
        }

        //calculate the vectors
        double vectorX = frameVelocityX * time;
        double vectorY = frameVelocityY * time;

        //vector 'a' is the remaining velocity
        //vector 'b' is the normals

        //calculate remaining velocity
        //this is vector 'a'
        //the normals are vector 'b'
        double remainVelX = frameVelocityX * (1.0 - time);
        double remainVelY = frameVelocityY * (1.0 - time);

        //calculate dot production
        double dotProduction = remainVelX * normalX + remainVelY * normalY;

        //calculate projection
        double projectionX = (dotProduction / (normalX * normalX + normalY * normalY) ) * normalX;
        double projectionY = (dotProduction / (normalX * normalX + normalY * normalY) ) * normalY;

        //I don't even fucking know what's what anymore
        //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH
        //but this is just creating the 'into the wall' force
        double slideX = remainVelX - projectionX;
        double slideY = remainVelY - projectionY;

        //calculate the total distance left to move
        double totalX = vectorX + slideX;
        double totalY = vectorY + slideY;

        //return the variables
       BoxCollision result = new BoxCollision(totalX, totalY, DeltaTime, true);
       result.normalX = normalX;
       result.normalY = normalY;
       return result;
    }
}