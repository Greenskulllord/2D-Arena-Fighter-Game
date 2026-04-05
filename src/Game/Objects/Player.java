package Game.Objects;
import Engine.Core.*;
import Engine.Components.*;
import Engine.Data.DataBase;
import Engine.Data.EntityData;
import KeyboardInput.InputControls;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Player extends Entity  {
    double width;
    double height;
    Image image;

    //make the constructor
    public Player(InputControls Controls) {
        EntityData data = DataBase.getTemplate("PLAYER");
        height = data.height;
        width = data.width;
        image = data.image;

        //add components
        TransformComponent position = new TransformComponent(50, 50);
        RenderComponent renderWall = new RenderComponent(new ImageView(image), position);
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
