package Engine.Components;
import Engine.Core.Component;
import Engine.Core.Entity;
import java.io.FileNotFoundException;

public class HealthComponent implements Component {
    public double health;
    Entity owner;
    DamageComponent damage;

    //boolean to check if owner took damage
    public boolean hit;
    public boolean heal;

    public HealthComponent (Entity owner, double health, DamageComponent damageComponent) {
        this.health = health;
        this.owner = owner;
        this.damage = damageComponent;

    }

    //on update, check if entity lost or gained health
    @Override
    public void update(double DeltaTime) throws FileNotFoundException {

        if (hit) {
            //take away from health
            health = health - damage.damage;
        }

        else if (heal){
            //add to health
            health = health + damage.damage;
        }
    }
}
