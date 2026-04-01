package Game;

import Engine.Components.CollisionComponent;
import Engine.Core.Component;
import javafx.scene.shape.Shape;

import java.io.FileNotFoundException;

public interface GameObjects {
    public void update(double DeltaTime) throws FileNotFoundException;
    public CollisionComponent getCollider();
    public Shape getNode();
    public <T extends Component> T getComponent(Class<T> componentClass);
}
