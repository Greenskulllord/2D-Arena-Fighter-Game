package Game.Objects;
import Game.Controllers.PlayerControllerComponent;
import Engine.Core.*;
import Engine.Components.*;
import Engine.Data.DataBase;
import Engine.Data.Types.EntityData;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;

public class Player extends Entity  {

    //make the constructor
    public Player(GameContext context, int spawnX, int spawnY, Scene scene) {
        EntityData data = DataBase.get("PLAYER");

        //add components
        TransformComponent position = new TransformComponent(spawnX, spawnY);
        RenderComponent render = new RenderComponent(new ImageView(data.image), position);
        CollisionComponent collisionComponent = new CollisionComponent(data.width, data.height, 0, 0, position, data.category, data.type);
        HealthComponent health = new HealthComponent(data.health, data.maxHealth, 1.0, 1.0, 1.0);
        DamageComponent damage = new DamageComponent(data.damage, 1);
        DeathComponent death = new DeathComponent(health, null);
        MovementComponent move = new MovementComponent(data.speed);
        InputComponent input = new InputComponent();
        StateComponent state = new StateComponent(0.15);


        this.addComponent(position);
        this.addComponent(render);
        this.addComponent(collisionComponent);
        this.addComponent(health);
        this.addComponent(damage);
        this.addComponent(death);
        this.addComponent(move);
        this.addComponent(input);
        this.addComponent(state);
        this.addComponent(new PlayerControllerComponent(this, context, scene, data));



    }
}
