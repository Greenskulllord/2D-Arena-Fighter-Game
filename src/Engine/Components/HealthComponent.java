package Engine.Components;
import Engine.Core.Component;
import Engine.Core.Entity;
import java.io.FileNotFoundException;

/**
 * The {@code HealthComponent} manages an entity's hit points and handles
 * logic for damage intake, healing, and invincibility frames.
 */
public class HealthComponent implements Component {
    /** The current health value of the entity. */
    public double health;

    /** The maximum health capacity for the entity. */
    public double maxHealth;

    /** Accumulated damage waiting to be processed. */
    public double pendingDamage;

    /** Accumulated healing waiting to be processed. */
    public double pendingHealing;

    /** The cooldown period required between taking damage instances. */
    public double damageCooldown;

    /** The cooldown period required between healing instances. */
    public double healingCooldown;

    /** The duration of invincibility frames (Iframes) after taking damage. */
    public double IframesDuration;

    /**
     * Constructs a {@code HealthComponent} with specified health and timing stats.
     *
     * @param health          The initial health value.
     * @param maxHealth       The maximum possible health.
     * @param damageCooldown  Cooldown time after taking damage.
     * @param healingCooldown Cooldown time after healing.
     * @param IframeDuration  The duration of invulnerability after a hit.
     */
    public HealthComponent (double health, double maxHealth, double damageCooldown, double healingCooldown, double IframeDuration) {
        this.health = health;
        this.maxHealth = maxHealth;
        this.damageCooldown = damageCooldown;
        this.IframesDuration = IframeDuration;
        this.healingCooldown = healingCooldown;

    }

    @Override
    public void update(double DeltaTime)  {

    }
}