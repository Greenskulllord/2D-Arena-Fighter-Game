package Engine.System;
import Engine.Components.CollisionComponent;
import Engine.Components.DamageComponent;
import Engine.Components.HealthComponent;
import Engine.Events.CollisionEvent;

public class CombatSystem {

    public void onCollision(CollisionEvent event) {
        String categoryA = event.A.getComponent(CollisionComponent.class).category;
        String categoryB = event.B.getComponent(CollisionComponent.class).category;

        if (categoryA.equals("ATTACK")) {
            DamageComponent damageA = event.A.getComponent(DamageComponent.class);
            HealthComponent healthB = event.B.getComponent(HealthComponent.class);

            if (damageA != null && healthB != null) {
                switch (categoryB) {
                    case "PLAYER", "ENEMY": healthB.pendingDamage += damageA.damage; break;
                }
            }
        }


        if (categoryA.equals("HEAL")) {
            DamageComponent damageA = event.A.getComponent(DamageComponent.class);
            HealthComponent healthB = event.B.getComponent(HealthComponent.class);

            if (damageA != null && healthB != null) {
                switch (categoryB) {
                    case "PLAYER", "ENEMY": healthB.pendingHealing += damageA.damage; break;
                }
            }
        }
    }
}
