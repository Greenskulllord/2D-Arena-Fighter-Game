package Engine.System;
import Engine.Components.AnimationComponent;
import Engine.Components.CollisionComponent;
import Engine.Components.DeathComponent;
import Engine.Components.RenderComponent;
import Engine.Core.ActiveEntities;
import Engine.Core.Component;
import Engine.Core.Entity;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class CleanUpSystem {
    private final Pane parentPane;
    public CleanUpSystem(Pane parentPane) {
     this.parentPane = parentPane;

    }

    public void update() {

        for (int i = ActiveEntities.getActiveEntities().size() - 1; i >= 0; i--) {
            Entity owner = ActiveEntities.getActiveEntities().get(i);
            DeathComponent death = owner.getComponent(DeathComponent.class);

            if (death != null && !death.isAlive) {

                CollisionComponent coll = owner.getComponent(CollisionComponent.class);
                //check if its player

                if (coll != null && coll.type.equals("PLAYER")) {
                    //run a respawn system or like a game over system
                    death.isAlive = true;
                    break;
                }

                RenderComponent render = owner.getComponent(RenderComponent.class);

                //remove the node
                if (render != null && render.getNode() != null) {
                    parentPane.getChildren().remove(render.getNode());
                }

                //kill timelines/animations
                AnimationComponent ani = owner.getComponent(AnimationComponent.class);

                if (ani != null) {
                    ani.destroy();
                }

                //remove components
                owner.removeAllComponents(Component.class);

                //add to a trash list
                ActiveEntities.fillTrashList(owner);
            }
        }
    }
}
