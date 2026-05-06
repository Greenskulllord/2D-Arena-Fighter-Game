package Engine.Components;
import Engine.Core.Component;
import Engine.Core.Entity;
import Engine.System.CleanUpSystem;
import java.io.FileNotFoundException;

/**
 * The {@code DeathComponent} manages the life status of an entity by tracking
 * its health and remaining lifetime.
 */
public class DeathComponent implements Component {

    /** The health state associated with this entity. */
    public final HealthComponent health;

    /** The lifetime tracker associated with this entity. */
    public final LifetimeComponent life;

    /** Flag indicating whether the entity is currently alive. */
    public boolean isAlive;

    /**
     * Constructs a {@code DeathComponent} linked to health and lifetime data.
     *
     * @param health The {@link HealthComponent} to monitor.
     * @param life   The {@link LifetimeComponent} to monitor.
     */
    public DeathComponent(HealthComponent health, LifetimeComponent life) {
        this.health = health;
        this.life = life;
        this.isAlive = true;

    }

    @Override
    public void update(double DeltaTime) {

    }
}