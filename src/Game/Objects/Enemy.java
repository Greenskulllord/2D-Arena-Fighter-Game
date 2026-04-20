package Game.Objects;
import Engine.Components.*;
import Engine.Core.Entity;
import Engine.Data.DataBase;
import Engine.Data.EntityData;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Enemy extends Entity {

    public Enemy (int spawnX, int spawnY) {
        EntityData data = DataBase.getTemplate("ENEMY");

        //make components
        TransformComponent position = new TransformComponent(spawnX, spawnY);
        RenderComponent render = new RenderComponent(new ImageView(data.image), position);
        CollisionComponent collisionComponent = new CollisionComponent(data.width, data.height, 0, 0, position, data.category, data.type);
        HealthComponent health = new HealthComponent(data.health, data.maxHealth);
        DamageComponent damage = new DamageComponent(data.damage, 1.0, 1);
        DeathComponent death = new DeathComponent(health, null);

        //add components
        this.addComponent(position);
        this.addComponent(render);
        this.addComponent(collisionComponent);
        this.addComponent(health);
        this.addComponent(damage);
        this.addComponent(death);
    }
}
