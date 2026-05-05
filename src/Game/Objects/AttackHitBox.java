package Game.Objects;

import Engine.Components.*;
import Engine.Core.ActiveEntities;
import Engine.Core.Entity;
import Engine.Data.DataBase;
import Engine.Data.EntityData;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class AttackHitBox extends Entity {

    public AttackHitBox(double width, double height, double spawnX, double spawnY, Entity owner) {
        EntityData data = DataBase.getTemplate("ATTACKBOX");

        DamageComponent ownerDamage = owner.getComponent(DamageComponent.class);
        if (ownerDamage == null) return;

        double damageAmount = ownerDamage.damage;
        double critMultiplier = ownerDamage.critMultiplier;

        //make everything

        TransformComponent trans = new TransformComponent(spawnX, spawnY);
        CollisionComponent coll = new CollisionComponent(width, height, 0, 0, trans, data.category, data.type);
        AnimationComponent ani = new AnimationComponent("animation_slash", 30, 100, 100);
        RenderComponent render = new RenderComponent(ani.frame, trans);
        LifetimeComponent life = new LifetimeComponent(0.25);
        DeathComponent death = new DeathComponent(null, life);
        DamageComponent damage = new DamageComponent(damageAmount, critMultiplier, 1);
        OwnerComponent own = new OwnerComponent(owner);

        //add components
        this.addComponent(trans);
        this.addComponent(coll);
        this.addComponent(ani);
        this.addComponent(render);
        this.addComponent(life);
        this.addComponent(death);
        this.addComponent(damage);
        this.addComponent(own);
    }
}
