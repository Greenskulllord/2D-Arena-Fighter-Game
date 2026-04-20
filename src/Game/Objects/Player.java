package Game.Objects;
import Engine.Core.*;
import Engine.Components.*;
import Engine.Data.DataBase;
import Engine.Data.EntityData;
import Engine.System.MovementSystem;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;

/* todo update player class to handle abilities,
    dashes, attacks, etc. this will be mostly updated in
    input component
 */
public class Player extends Entity  {
    GameContext context;

    //make the constructor
    public Player(GameContext context, int spawnX, int spawnY, Scene scene) {
        EntityData data = DataBase.getTemplate("PLAYER");

        //add components
        TransformComponent position = new TransformComponent(spawnX, spawnY);
        RenderComponent render = new RenderComponent(new ImageView(data.image), position);
        CollisionComponent collisionComponent = new CollisionComponent(data.width, data.height, 0, 0, position, data.category, data.type);
        HealthComponent health = new HealthComponent(data.health, data.maxHealth, 1.0, 1.0, 1.0);
        DamageComponent damage = new DamageComponent(data.damage, 1.1, 1);
        DeathComponent death = new DeathComponent(health, null);
        MovementComponent move = new MovementComponent(200.0);

        //building it like in factories now!! WHOO
        this.addComponent(position);
        this.addComponent(render);
        this.addComponent(collisionComponent);
        this.addComponent(health);
        this.addComponent(damage);
        this.addComponent(death);
        this.addComponent(move);
        this.addComponent(new InputComponent(this, context, scene));
    }

     /*
    =========================
        helper methods
    =========================
     */

}
