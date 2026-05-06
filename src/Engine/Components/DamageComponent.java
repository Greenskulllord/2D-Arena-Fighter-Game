package Engine.Components;
import Engine.Core.Component;
import java.io.FileNotFoundException;

/**
 * The {@code DamageComponent} class stores statistical data related to offensive capabilities.
 * It defines how much damage an entity deals and how effectively it can land critical hits.
 */
public class DamageComponent implements Component {

    /** The base amount of damage dealt by the entity. */
    public double damage;

    /** The multiplier applied to the base damage during a critical hit. */
    public double critMultiplier;

    /** The frequency at which the entity can perform attacks. */
    public double attackSpeed;

    //code left for when I implement damage types
    enum damageType {
        BASE;
    }

    /**
     * Constructs a new {@code DamageComponent} with specified damage and critical values.
     *
     * @param damageAmount   The base value for damage output.
     * @param critMultiplier The factor by which damage is increased on critical strikes.
     */
    public DamageComponent(double damageAmount, double critMultiplier) {
        this.damage = damageAmount;
        this.critMultiplier = critMultiplier;
        this.attackSpeed = attackSpeed;

    }

    /**
     * Updates the component logic.
     *
     * @param DeltaTime The time elapsed since the last update call.
     */
    @Override
    public void update(double DeltaTime)  {

    }
}