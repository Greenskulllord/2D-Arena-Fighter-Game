package Game.Objects;
import Engine.Components.*;
import Game.Controllers.BlackDudeControllerComponent;
import Engine.Core.Entity;
import Engine.Core.GameContext;
import Engine.Data.DataBase;
import Engine.Data.Types.EntityData;
import javafx.scene.image.ImageView;

public class Enemy extends Entity {

    public Enemy (GameContext context, int spawnX, int spawnY) {
        EntityData data = DataBase.getTemplate("ENEMY");

        //make components
        TransformComponent position = new TransformComponent(spawnX, spawnY);
        RenderComponent render = new RenderComponent(new ImageView(data.image), position);
        CollisionComponent collisionComponent = new CollisionComponent(data.width, data.height, 0, 0, position, data.category, data.type);
        HealthComponent health = new HealthComponent(data.health, data.maxHealth, 1, 1, 0.3);
        DamageComponent damage = new DamageComponent(10, 1.0);
        DeathComponent death = new DeathComponent(health, null);
        StateComponent state = new StateComponent(0.15);
        MovementComponent move = new MovementComponent(100);
        InputComponent input = new InputComponent();
        BlackDudeControllerComponent controller = new BlackDudeControllerComponent(this, context, data);


        //add components
        this.addComponent(position);
        this.addComponent(render);
        this.addComponent(collisionComponent);
        this.addComponent(health);
        this.addComponent(damage);
        this.addComponent(death);
        this.addComponent(state);
        this.addComponent(input);
        this.addComponent(move);
        this.addComponent(controller);
    }
}
