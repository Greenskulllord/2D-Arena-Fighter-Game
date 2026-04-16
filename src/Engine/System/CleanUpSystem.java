package Engine.System;
import Engine.Components.DeathComponent;
import Engine.Components.RenderComponent;
import Engine.Core.ActiveEntities;
import Engine.Core.Component;
import Engine.Core.Entity;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class CleanUpSystem {
    private Pane parentPane;
    public CleanUpSystem(Pane parentPane) {
     this.parentPane = parentPane;

    }

    public void update() {

        for (int i = ActiveEntities.getActiveEntities().size() - 1; i >= 0; i--) {
            Entity owner = ActiveEntities.getActiveEntities().get(i);
            DeathComponent death = owner.getComponent(DeathComponent.class);

            if (death != null && !death.isAlive) {
                Node node = owner.getComponent(RenderComponent.class).getNode();

                //remove the node
                parentPane.getChildren().remove(node);

                //remove components
                owner.removeAllComponents(Component.class);

                //add to a trash list
                ActiveEntities.fillTrashList(owner);
                System.out.println("\nadded to trashList");
            }
        }
    }
}
