package Engine.System;

import Engine.Components.RenderComponent;
import Engine.Core.ActiveEntities;
import Engine.Core.Entity;
import javafx.scene.layout.Pane;

public class Spawner {
    private Pane world;

    public void start(Pane world) {
        this.world = world;

    }

    //spawns the entity
    public void spawn(Entity entity) {
        //add entity to list of active entities
        ActiveEntities.fillActiveEntitiesList(entity);

        //call render component to find node
        RenderComponent render = entity.getComponent(RenderComponent.class);

        //add image to entity
        if (render != null) {
            world.getChildren().add(render.getNode());
            render.update(0);
        }
    }
}
