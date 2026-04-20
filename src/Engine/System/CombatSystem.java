package Engine.System;
import Engine.Components.CollisionComponent;
import Engine.Components.DamageComponent;
import Engine.Components.HealthComponent;
import Engine.Core.Entity;
import Engine.Events.CollisionEvent;

public class CombatSystem {

    // I do not know if this works or not

    public void onCollision(CollisionEvent event) {
        handleCombat(event.A, event.B);
        handleCombat(event.B, event.A);
        handleHealing(event.A, event.B);
        handleHealing(event.B, event.A);
    }

    private void handleCombat(Entity attacker, Entity target) {
        CollisionComponent coll = attacker.getComponent(CollisionComponent.class);
        if (coll == null) return;

        if (coll.category.equals("ATTACK")) {
            DamageComponent damageA = attacker.getComponent(DamageComponent.class);
            HealthComponent healthB = target.getComponent(HealthComponent.class);

            if (damageA != null && healthB != null) {
                healthB.pendingDamage += damageA.damage;

            }
        }
    }

    private void handleHealing (Entity healer, Entity target) {
        CollisionComponent coll = healer.getComponent(CollisionComponent.class);
        if (coll == null) return;

        if (coll.category.equals("HEAL")) {
            DamageComponent damageA = healer.getComponent(DamageComponent.class);
            HealthComponent healthB = target.getComponent(HealthComponent.class);

            if (damageA != null && healthB != null) {
                healthB.pendingHealing += damageA.damage;

            }
        }
    }

}
