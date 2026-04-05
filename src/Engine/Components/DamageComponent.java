package Engine.Components;
import Engine.Core.Component;
import java.io.FileNotFoundException;

public class DamageComponent implements Component {

    double damage;

    public DamageComponent(double amount) {
        this.damage = amount;

    }

    @Override
    public void update(double DeltaTime) throws FileNotFoundException {

    }
}
