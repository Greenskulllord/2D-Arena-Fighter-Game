package Engine.Components;
import Engine.Core.Component;
import java.io.FileNotFoundException;

public class DamageComponent implements Component {

    public double damage;
    public float critMultiplier;

    enum damageType {
        BASE;
    }

    public DamageComponent(double damageAmount, float critMultiplier, String damageType) {
        this.damage = damageAmount;
        this.critMultiplier = critMultiplier;

    }

    @Override
    public void update(double DeltaTime) throws FileNotFoundException {

    }
}
