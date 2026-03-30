package GameComponents;

import Engine.Components.CollisionComponent;
import Engine.Components.RenderComponent;
import Engine.Components.TransformComponent;
import Engine.Core.Component;
import Engine.Core.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Wall implements GameObjects{
    Entity wall = new Entity();
    Rectangle rectangle = new Rectangle(50, 50, Color.BLUE);

    //add components
    TransformComponent wallPOS = new TransformComponent(150, 150);
    RenderComponent renderWall = new RenderComponent(rectangle, wallPOS);
    CollisionComponent collisionComponent = new CollisionComponent(50, 50, 0, 0, wallPOS);

    public Wall() {
        wall.addComponent(wallPOS);
        wall.addComponent(collisionComponent);
        wall.addComponent(renderWall);
    }

     /*
    =========================
        helper methods
    =========================
     */

    public void update(double DeltaTime) {
        wall.update(DeltaTime);
    }

    @Override
    public CollisionComponent getCollider() {
        return wall.getComponent(CollisionComponent.class);
    }

    public Shape getNode() {
        return rectangle;
    }

    @Override
    public <T extends Component> T getComponent(Class<T> componentClass) {
        return wall.getComponent(componentClass);
    }

}
