package Engine.Math;
import Engine.Components.CollisionComponent;
import Engine.Components.TransformComponent;
import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;

// https://www.metanetsoftware.com/technique/tutorialA.html this is the main math code I followed
public class SweptCollision {
    //declare variables
    double normalX_;
    double normalY_;
    double time_;

    SweptCollision (double normalX, double normalY, double time) {
        this.normalX_ = normalX;
        this.normalY_ = normalY;
        this.time_ = time;

    }

    //create a method for handling swept AABB collision
    public static SweptCollision boxBoxCollision(TransformComponent transformComponent,
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
        double frameVelocityX = transformComponent.velocityX * DeltaTime;
        double frameVelocityY = transformComponent.velocityY * DeltaTime;

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

        double entryTimeX = NEGATIVE_INFINITY;
        double entryTimeY = NEGATIVE_INFINITY;
        double exitTimeX = POSITIVE_INFINITY;
        double exitTimeY = POSITIVE_INFINITY; //what?

        //I don't even know what kind of math is going on here
        //I looked it up :(
        if (frameVelocityX != 0) {
            entryTimeX = nearX / frameVelocityX;
            exitTimeX = farX / frameVelocityX;
        }

        if (frameVelocityY != 0) {
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
        if (entry > exit || entry < 0 || entry > 1.0 || entryTimeX == NEGATIVE_INFINITY || entryTimeY == NEGATIVE_INFINITY) {
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
