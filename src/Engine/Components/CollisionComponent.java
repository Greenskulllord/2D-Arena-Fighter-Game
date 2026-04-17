package Engine.Components;
import Engine.Core.Component;
import javax.swing.*;

//this is a component that handles collision
public class CollisionComponent implements Component {
    //call variables
    public double height; //for height of hitbox
    public double width; //for width of hitbox
    double offsetX;
    double offsetY;

    TransformComponent transformComponent; //component for x and y
    public String category;
    public String type;

    public CollisionComponent(double Width, double Height,
                              double offsetX, double offsetY, TransformComponent transform, String category, String type) {

        this.category = category;
        this.type = type;
        this.transformComponent = transform;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.height = Height;
        this.width = Width;

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
