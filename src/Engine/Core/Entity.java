package Engine.Core;

import java.util.ArrayList;
import java.util.List;

public class Entity {

    public final List<Component> components = new ArrayList<>();

    public void addComponent(Component component) {
        components.add(component);
    }

    public void update(double DeltaTime) {
        for (Component component : components) {
            component.update(DeltaTime);
        }
    }

}
