package Engine.System;
import Engine.Components.*;
import Engine.Core.Entity;
import Engine.Events.CollisionEvent;
import Engine.Math.Utils;
import Engine.Math.Vector2D;

public class CombatSystem {

    Utils utils = new Utils();
    Vector2D v;

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
                healthB.pendingDamage += damageA.damage * damageA.critMultiplier;

                TransformComponent targetTrans = target.getComponent(TransformComponent.class);
                TransformComponent attackTrans = attacker.getComponent(TransformComponent.class);
                StateComponent targetState = target.getComponent(StateComponent.class);

                //apply knockback
                if (targetTrans != null && attackTrans != null) {

                    double dx = targetTrans.x - attackTrans.x;
                    double dy = targetTrans.y - attackTrans.y;

                    v = utils.normalize(dx, dy);

                    targetTrans.velocityX = v.x;
                    targetTrans.velocityY = v.y;
                }

                //apply hit stun
                if (targetState != null && healthB.IframesDuration <= 0) {
                    targetState.changeState(StateComponent.state.HIT_STUN);

                }

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
