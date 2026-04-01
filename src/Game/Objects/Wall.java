package Game.Objects;

import Engine.Components.CollisionComponent;
import Engine.Components.RenderComponent;
import Engine.Components.TransformComponent;
import Engine.Core.ActiveEntities;
import Engine.Core.Component;
import Engine.Core.Entity;
import Game.GameObjects;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.io.FileNotFoundException;

public class Wall implements GameObjects {
    Entity wall = new Entity();
    Rectangle rectangle = new Rectangle(50, 50, Color.BLUE);

    //add components
    TransformComponent wallPOS = new TransformComponent(150, 150);
    RenderComponent renderWall = new RenderComponent(rectangle, wallPOS);
    CollisionComponent collisionComponent = new CollisionComponent(50, 50, 0, 0, wallPOS, "WALL");

    public Wall() {
        wall.addComponent(wallPOS);
        wall.addComponent(collisionComponent);
        wall.addComponent(renderWall);
        ActiveEntities.fillActiveEntitiesList(wall);
    }

     /*
    =========================
        helper methods
    =========================
     */

    public void update(double DeltaTime) throws FileNotFoundException {
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
