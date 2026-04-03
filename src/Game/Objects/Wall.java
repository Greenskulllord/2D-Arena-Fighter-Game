package Game.Objects;

import Engine.Components.CollisionComponent;
import Engine.Components.RenderComponent;
import Engine.Components.TransformComponent;
import Engine.Core.ActiveEntities;
import Engine.Core.Component;
import Engine.Core.DataBase;
import Engine.Core.Entity;
import Game.GameObjects;
import Initialization.EntityData;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.io.FileNotFoundException;

public class Wall extends Entity implements GameObjects {
    double height;
    double width;

    public Wall() {
        EntityData data = DataBase.getTemplate("WALL");
        height = data.height;
        width = data.width;

        Rectangle rectangle = new Rectangle(width, height, Color.BLUE);

        //add components
        TransformComponent position = new TransformComponent(150, 150);
        RenderComponent renderWall = new RenderComponent(rectangle, position);
        CollisionComponent collisionComponent = new CollisionComponent(width, height, 0, 0, position, "WALL");

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
