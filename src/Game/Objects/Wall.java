package Game.Objects;
import Engine.Components.CollisionComponent;
import Engine.Components.RenderComponent;
import Engine.Components.TransformComponent;
import Engine.Data.DataBase;
import Engine.Core.Entity;
import Engine.Data.EntityData;
import Engine.Managers.RoomMapManager;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Wall extends Entity {
    double height;
    double width;

    public Wall(int spawnX, int spawnY) {
        EntityData data = DataBase.getTemplate("WALL");
        Rectangle rectangle = new Rectangle(data.width, data.height, Color.BLACK);

        //add components
        TransformComponent position = new TransformComponent(spawnX, spawnY);
        RenderComponent renderWall = new RenderComponent(rectangle, position);
        CollisionComponent collisionComponent = new CollisionComponent(data.width, data.height, 0, 0, position, data.category, data.type);

        this.addComponent(position);
        this.addComponent(collisionComponent);
        this.addComponent(renderWall);
    }

     /*
    =========================
        helper methods
    =========================
     */

}
