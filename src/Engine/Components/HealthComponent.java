package Engine.Components;
import Engine.Core.Component;
import Engine.Core.Entity;
import java.io.FileNotFoundException;

public class HealthComponent implements Component {
    public double health;
    public double maxHealth;
    Entity owner;

    //variables to check if owner took damage or healed
    public double pendingDamage;
    public double pendingHealing;

    public HealthComponent (Entity owner, double health, double maxHealth) {
        this.health = health;
        this.maxHealth = maxHealth;
        this.owner = owner;


    }

    //on update, check if entity lost or gained health
    @Override
    public void update(double DeltaTime) throws FileNotFoundException {


    }
}
