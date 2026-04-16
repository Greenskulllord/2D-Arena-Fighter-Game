package Engine.Components;
import Engine.Core.Component;
import Engine.Core.Entity;
import Engine.System.CleanUpSystem;
import java.io.FileNotFoundException;

public class DeathComponent implements Component {

    private Entity owner;
    private final HealthComponent health;
    private final LifetimeComponent life;
    public boolean isAlive;

    DeathComponent(Entity owner, HealthComponent health, LifetimeComponent life) {
        this.owner = owner;
        this.health = health;
        this.life = life;
        this.isAlive = true;

    }

    @Override
    public void update(double DeltaTime) throws FileNotFoundException {


    }
}
