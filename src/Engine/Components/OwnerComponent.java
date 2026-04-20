package Engine.Components;

import Engine.Core.Component;
import Engine.Core.Entity;

public class OwnerComponent implements Component {
    Entity owner;

    public OwnerComponent(Entity owner) {
        this.owner = owner;

    }


    @Override
    public void update(double DeltaTime) {

    }
}
