package Game.Objects;
import Engine.Core.*;
import Engine.Components.*;
import Game.GameObjects;
import Initialization.EntityData;
import KeyboardInput.InputControls;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.io.FileNotFoundException;

public class Player extends Entity implements GameObjects  {
    double width;
    double height;

    //make the constructor
    public Player(InputControls Controls) {
        EntityData data = DataBase.getTemplate("PLAYER");
        height = data.height;
        width = data.width;

        //the player itself, aka the image for the player.
        Rectangle rectangle = new Rectangle(width, height, Color.WHITE);

        //add components
        TransformComponent position = new TransformComponent(50, 50);
        RenderComponent renderWall = new RenderComponent(rectangle, position);
        CollisionComponent collisionComponent = new CollisionComponent(width, height, 0, 0, position, "PLAYER");

        //building it like in factories now!! WHOO
        this.addComponent(position);
        this.addComponent(renderWall);
        this.addComponent(collisionComponent);
        this.addComponent(new InputComponent(this, Controls));

    }

     /*
    =========================
        helper methods
    =========================
     */

}
