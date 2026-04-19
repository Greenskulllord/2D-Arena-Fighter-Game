package Game.Objects;

import Engine.Components.*;
import Engine.Core.ActiveEntities;
import Engine.Core.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class AttackHitBox extends Entity {

    AttackHitBox (int spawnX, int spawnY, Entity owner) {

        double damageAmount = 0.0;
        double critMultiplier = 0.0;

        Rectangle rectangle = new Rectangle(50, 50, Color.BLUE);

        TransformComponent trans = new TransformComponent(spawnX, spawnY);
        CollisionComponent coll = new CollisionComponent(50, 50, 0, 0, trans, "HITBOX", "ATTACK");
        RenderComponent render = new RenderComponent(rectangle, trans);
        LifetimeComponent life = new LifetimeComponent(150.0);
        DeathComponent death = new DeathComponent(null, life);
        DamageComponent damage = new DamageComponent(damageAmount, critMultiplier);

        //sum way to reference owner of hitbox
        for (int i = ActiveEntities.getActiveEntities().size() - 1; i >= 0; i--) {

            //get the owner of hitbox
            Entity entityA = ActiveEntities.getActiveEntities().get(i);
            CollisionComponent collA = entityA.getComponent(CollisionComponent.class);
            DamageComponent damageA = entityA.getComponent(DamageComponent.class);

            if (collA == null) continue; // skip if null

            if (collA.category.equals("PLAYER")) {
                damageAmount = damageA.damage; //hopefully this works
            }
        }

        //add components after finding damage to owner
        this.addComponent(trans);
        this.addComponent(coll);
        this.addComponent(render);
        this.addComponent(life);
        this.addComponent(death);
        this.addComponent(damage);

    }
}
