package Engine.Components;
import Engine.Core.Component;
import Engine.Core.Entity;
import java.io.FileNotFoundException;

public class HealthComponent implements Component {
    public double health;
    public double maxHealth;

    //variables to check if owner took damage or healed
    public double pendingDamage;
    public double pendingHealing;
    public double damageCooldown;
    public double healingCooldown;
    public double IframesDuration;


    public HealthComponent (double health, double maxHealth, double damageCooldown, double healingCooldown, double IframeDuration) {
        this.health = health;
        this.maxHealth = maxHealth;
        this.damageCooldown = damageCooldown;
        this.IframesDuration = IframeDuration;

    }

    //on update, check if entity lost or gained health
    @Override
    public void update(double DeltaTime)  {

    }
}
