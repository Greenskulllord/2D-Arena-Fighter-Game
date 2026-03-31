package Engine.Components;

import Engine.Core.Component;
import Engine.Math.BoxCollision;

import javax.swing.*;

public class CollisionComponent implements Component {
    //call variables
    double height; //for height of hitbox
    double width; //for width of hitbox
    double offsetX;
    double offsetY;
    TransformComponent transformComponent; //component for x and y
    CollisionComponent test;

    public CollisionComponent(double Width, double Height,
                              double offsetX, double offsetY, TransformComponent transform) {

        this.transformComponent = transform;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.height = Height;
        this.width = Width;

    }

    //check for collision
    public boolean checkCollision(CollisionComponent other) {
        //AABB math collision checker for 2D games
        if (transformComponent.x + offsetX < other.transformComponent.x + other.offsetX + other.width
                && transformComponent.x + offsetX + width > other.transformComponent.x + other.offsetX
                && transformComponent.y + offsetY < other.transformComponent.y + other.offsetY + other.height
                && transformComponent.y + offsetY + height > other.transformComponent.y + other.offsetY) {
            return true;
        }
        return false;
    }

    @Override
    public void update(double DeltaTime) {


    }

      /*
    =========================
        helper methods
    =========================
     */

    public double getMinX() {
        return transformComponent.x + offsetX;
    }

    public double getMinY() {
        return transformComponent.y + offsetY;
    }

    public double getMaxX() {
        return transformComponent.x + offsetX + width;
    }

    public double getMaxY() {
        return transformComponent.y + offsetY + height;
    }

}
