package Engine.Math;
import Engine.Components.CollisionComponent;
import Engine.Components.TransformComponent;
import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;

//this holds all the math for complex collision
// https://www.metanetsoftware.com/technique/tutorialA.html this is the main math code I followed
public class SweptCollision {
    //declare variables
    public double normalX_;
    public double normalY_;
    public double time_;

    public SweptCollision(double normalX, double normalY, double time) {
        this.normalX_ = normalX;
        this.normalY_ = normalY;
        this.time_ = time;

    }

    //create a method for handling swept AABB collision
    public static SweptCollision boxCollision(TransformComponent transformA, TransformComponent transformB,
                                         CollisionComponent owner, CollisionComponent other, double DeltaTime) {
        //this is so fucking big that imma just break it down
        //so it's easier to read

        //-----------------------
        //    Gather The Data
        // ----------------------

        //get 'owner' sides
        double ownerMinX = owner.getMinX();
        double ownerMinY = owner.getMinY();
        double ownerMaxX = owner.getMaxX();
        double ownerMaxY = owner.getMaxY();

        //get owner frame velocity X and Y
        double frameVelocityX = (transformA.velocityX - transformB.velocityX) * DeltaTime;
        double frameVelocityY = (transformA.velocityY - transformB.velocityY) * DeltaTime;

        //get 'other' sides
        double otherMinX = other.getMinX();
        double otherMaxX = other.getMaxX();
        double otherMinY = other.getMinY();
        double otherMaxY = other.getMaxY();

        //-----------------------
        //    Calculate Distances
        // ----------------------

        //got to find how much distant is between the two object
        double nearX;
        double nearY;
        double farX;
        double farY;

        //distance for X
        if (frameVelocityX > 0) {
            nearX = otherMinX - ownerMaxX;
            farX = otherMaxX - ownerMinX;
        } else {
            nearX = otherMaxX - ownerMinX;
            farX = otherMinX - ownerMaxX;
        }
        //distance for Y
        if (frameVelocityY > 0) {
            nearY = otherMinY - ownerMaxY;
            farY = otherMaxY - ownerMinY;
        } else {
            nearY = otherMaxY - ownerMinY;
            farY = otherMinY - ownerMaxY;
        }

        //-----------------------
        //    Calculate Times
        // ----------------------

        double entryTimeX;
        double entryTimeY;
        double exitTimeX;
        double exitTimeY; //what?

        //I don't even know what kind of math is going on here
        //I looked it up :(
        if (frameVelocityX == 0) {
            //got to make sure to fix this fucking bug
            //only going diagonal gives collision without it
            if (ownerMaxX <= otherMinX || ownerMinX >= otherMaxX) return new SweptCollision(0,0,1.0);

            //if going straight right doesn't return zero
            //give infinity values
            entryTimeX = NEGATIVE_INFINITY;
            exitTimeX = POSITIVE_INFINITY;

        } else {
            entryTimeX = nearX / frameVelocityX;
            exitTimeX = farX / frameVelocityX;
        }

        //same for Y
        if (frameVelocityY == 0) {
            if (ownerMaxY <= otherMinY || ownerMinY >= otherMaxY) return new SweptCollision(0,0,1.0);

            //same thing for Y, fucking bugs
            entryTimeY = NEGATIVE_INFINITY;
            exitTimeY = POSITIVE_INFINITY;

        } else {
            entryTimeY = nearY / frameVelocityY;
            exitTimeY = farY / frameVelocityY;
        }

        //-----------------------
        //    Verify Collision
        // ----------------------

        //entry and exit should be what outputs, example: (0.2, 0.8)
        double entry = Math.max(entryTimeX, entryTimeY);
        double exit = Math.min(exitTimeX, exitTimeY); //huh?
        double normalX;
        double normalY;

        //we need to make sure the entry and exit
        //is between 1.0 and 0.0 cause of invisible stopping
        //between player and wall
        //these are just describing impossible collision btw.
        if (entry > exit || entry < 0 || entry > 1.0) {
            //keep player moving
            return new SweptCollision(0, 0, 1.0);
        }

        //if the X overlaps Y, it hit a wall
        if(entryTimeX > entryTimeY) {
            normalY = 0.0;

            if (frameVelocityX > 0) {
                normalX = -1.0;
            } else {
                normalX = 1.0;
            }
        }

        //if the Y overlaps X, it hit a wall
        else {
            normalX = 0.0;
            //I want to fucking die
            if (frameVelocityY > 0) {
                normalY = -1.0;
            } else {
                normalY = 1.0;
            }
        }

        //------------------------
        //    Return the result
        // -----------------------
        //yay
        return new SweptCollision(normalX, normalY, entry);
    }
}
