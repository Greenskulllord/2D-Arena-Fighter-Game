package Engine.Components;
import Engine.Core.Component;
import java.io.FileNotFoundException;

public class DamageComponent implements Component {

    public double damage;
    public double critMultiplier;

    //code left for when I implement damage types
    enum damageType {
        BASE;
    }

    public DamageComponent(double damageAmount, double critMultiplier) {
        this.damage = damageAmount;
        this.critMultiplier = critMultiplier;

    }

    @Override
    public void update(double DeltaTime) throws FileNotFoundException {

    }
}
