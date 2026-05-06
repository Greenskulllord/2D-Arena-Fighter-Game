package Engine.Components;
import Engine.Core.Component;
import javafx.scene.Node;

/**
 * The {@code RenderComponent} facilitates the visual representation of an entity
 * by synchronizing a JavaFX {@link Node} with a {@link TransformComponent}.
 */
public class RenderComponent implements Component {
    Node node;
    TransformComponent transformComponent;

    /**
     * Constructs a {@code RenderComponent} that links a visual node to a transform.
     *
     * @param currentNode The JavaFX Node (shape, image, etc.) to be rendered.
     * @param transform   The {@link TransformComponent} used for positioning.
     */
    public RenderComponent(Node currentNode, TransformComponent transform) {
        this.node = currentNode;
        this.transformComponent = transform;

    }

    @Override
    public void update(double DeltaTime) {
        node.setTranslateX(transformComponent.x);
        node.setTranslateY(transformComponent.y);

    }

    /**
     * Retrieves the visual node associated with this component.
     *
     * @return The {@link Node} currently being rendered.
     */
    public Node getNode() { return node; }
}