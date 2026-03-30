package GameComponents;

import Engine.Components.CollisionComponent;
import Engine.Core.Component;
import javafx.scene.shape.Shape;

public interface GameObjects {
    public void update(double DeltaTime);
    public CollisionComponent getCollider();
    public Shape getNode();
    public <T extends Component> T getComponent(Class<T> componentClass);
}
