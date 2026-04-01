package Engine.Components;
import Engine.Core.Component;
import javax.swing.*;

//this is a compoonent that handles collision
public class CollisionComponent implements Component {
    //call variables
    double height; //for height of hitbox
    double width; //for width of hitbox
    double offsetX;
    double offsetY;
    TransformComponent transformComponent; //component for x and y
    CollisionComponent test;
    public String type;
    public CollisionComponent(double Width, double Height,
                              double offsetX, double offsetY, TransformComponent transform, String type) {
        this.type = type;
        this.transformComponent = transform;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.height = Height;
        this.width = Width;

    }

    @Override
    public void update(double DeltaTime) {
        //run the swept math and collision fixer logic

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
