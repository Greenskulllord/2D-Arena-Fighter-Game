package Game.Objects;

import Engine.Components.*;
import Engine.Core.ActiveEntities;
import Engine.Core.Entity;
import Engine.Data.DataBase;
import Engine.Data.EntityData;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class AttackHitBox extends Entity {

    public AttackHitBox(int spawnX, int spawnY, Entity owner) {
        EntityData data = DataBase.getTemplate("ATTACKBOX");

        DamageComponent ownerDamage = owner.getComponent(DamageComponent.class);
        if (ownerDamage == null) return;

        double damageAmount = ownerDamage.damage;
        double critMultiplier = ownerDamage.critMultiplier;

        //make everything
        Rectangle rectangle = new Rectangle(50, 50, Color.BLUE);
        TransformComponent trans = new TransformComponent(spawnX, spawnY);
        CollisionComponent coll = new CollisionComponent(50, 50, 0, 0, trans, data.category, data.type);
        RenderComponent render = new RenderComponent(rectangle, trans);
        LifetimeComponent life = new LifetimeComponent(10.0);
        DeathComponent death = new DeathComponent(null, life);
        DamageComponent damage = new DamageComponent(damageAmount, critMultiplier, 1);

        //add components
        this.addComponent(trans);
        this.addComponent(coll);
        this.addComponent(render);
        this.addComponent(life);
        this.addComponent(death);
        this.addComponent(damage);
    }
}
