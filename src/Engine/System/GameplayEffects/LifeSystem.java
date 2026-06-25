package Engine.System.GameplayEffects;

import Engine.Components.DamageComponent;
import Engine.Components.DeathComponent;
import Engine.Components.HealthComponent;
import Engine.Components.LifetimeComponent;
import Engine.Core.ActiveEntities;
import Engine.Core.Entity;

public class LifeSystem {

    public LifeSystem() {
    }

    public void update(double DeltaTime) {

        for (int i = ActiveEntities.getActiveEntities().size() - 1; i >= 0; i--) {
            //roll checks for every entity and run code if they got hit or healed

            //get the entity
            Entity owner = ActiveEntities.getActiveEntities().get(i);

            //get components of the owner entity
            HealthComponent HP = owner.getComponent(HealthComponent.class);
            DamageComponent DMG = owner.getComponent(DamageComponent.class);
            DeathComponent death = owner.getComponent(DeathComponent.class);
            LifetimeComponent lifeTime = owner.getComponent(LifetimeComponent.class);

            //skip an entity that has nothing
            if (HP == null && DMG == null && death == null && lifeTime == null) { continue; }

            //this code is ran for entities health values
            if (HP != null && death != null && death.health != null) {

                //check if entity got hit
                if (HP.damageCooldown > 0) {
                    HP.damageCooldown -= DeltaTime;
                    HP.pendingDamage = 0;
                }
                else if (HP.pendingDamage > 0) {
                    //code to run "take damage" logic
                    HP.health -= HP.pendingDamage;
                    HP.pendingDamage = 0;
                    HP.damageCooldown = HP.IframesDuration;
//                    System.out.println("HP: " + HP.health);
                }

                //check if entity got healed
                if (HP.healingCooldown > 0) {
                    HP.healingCooldown -= DeltaTime;
                    HP.pendingHealing = 0;
                }
                else if (HP.pendingHealing > 0) {
                    //code to run heal logic
                    HP.health += HP.pendingHealing;

                    //prevents from going over max cap
                    if (HP.health >= HP.maxHealth) {
                        HP.health = HP.maxHealth;
                    }

                    HP.pendingHealing = 0;
                    HP.healingCooldown = HP.IframesDuration;
                }

                //if hp is zero, mark as dead
                if (HP.health <= 0) {
                    death.isAlive = false;//flag as dead

                }

            }

            //this code is ran for hitboxes/projectiles
            if (lifeTime != null && death != null && death.life != null) {
                lifeTime.startTimer -= DeltaTime; //start timer

                //kill when timer has ended
                if (lifeTime.startTimer <= 0) {
                    death.isAlive = false; //marks as dead

                }

            }
        }
    }
}
