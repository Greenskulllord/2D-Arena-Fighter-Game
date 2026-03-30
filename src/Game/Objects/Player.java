package Game.Objects;
import Engine.Core.*;
import Engine.Components.*;
import Game.GameObjects;
import KeyboardInput.InputControls;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Player implements GameObjects {
    double width = 50;
    double height = 50;

    //make the player entity, this is a ghost object for now.
    Entity player = new Entity();

    //the player itself, aka the image for the player.
    Rectangle rectangle = new Rectangle(width, height, Color.WHITE);

    //call the components
    TransformComponent playerPOS = new TransformComponent(50, 50);
    RenderComponent renderPlayer = new RenderComponent(rectangle, playerPOS);
    CollisionComponent collisionComponent = new CollisionComponent(width, height, 0, 0, playerPOS);

    //make the constructor
    public Player(InputControls Controls) {
        //building it like in factories now!! WHOO
        player.addComponent(playerPOS);
        player.addComponent(collisionComponent);
        player.addComponent(renderPlayer);
        player.addComponent(new InputComponent(player, Controls));

    }

     /*
    =========================
        helper methods
    =========================
     */

    public void update(double DeltaTime) {
        player.update(DeltaTime);
    }

    public CollisionComponent getCollider() {
        return player.getComponent(CollisionComponent.class);
    }

    public Shape getNode() {
        return rectangle;
    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        return player.getComponent(componentClass);
    }

}
