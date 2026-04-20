package Engine.Components;
import Engine.Core.Component;
import javafx.scene.Node;

//this is a component that handles sprite rendering
public class RenderComponent implements Component {
    Node node;
    TransformComponent transformComponent;

    public RenderComponent(Node currentNode, TransformComponent transform) {
        //tell java the shape inputted is the object this component is attached too
        //shapes are just nodes pretty much, hoping shapes can be images too
        this.node = currentNode;
        this.transformComponent = transform;
    }

    @Override
    public void update(double DeltaTime) {
        //using transform component, set the x and y to the node
        //this will snap the node onto the ghost object
        //making a visible object
       node.setTranslateX(transformComponent.x);
       node.setTranslateY(transformComponent.y);

    }

      /*
    =========================
        helper methods
    =========================
     */

    public Node getNode() { return node; }
}
