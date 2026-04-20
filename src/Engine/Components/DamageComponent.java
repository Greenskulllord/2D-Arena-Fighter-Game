package Engine.Components;
import Engine.Core.Component;
import java.io.FileNotFoundException;

public class DamageComponent implements Component {

    public double damage;
    public double critMultiplier;
    public double attackSpeed;

    //code left for when I implement damage types
    enum damageType {
        BASE;
    }

    public DamageComponent(double damageAmount, double critMultiplier, double attackSpeed) {
        this.damage = damageAmount;
        this.critMultiplier = critMultiplier;
        this.attackSpeed = attackSpeed;

    }

    @Override
    public void update(double DeltaTime)  {

    }
}
