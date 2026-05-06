package Engine.System;

import Engine.Components.RenderComponent;
import Engine.Core.ActiveEntities;
import Engine.Core.Entity;
import javafx.scene.layout.Pane;

/**
 * The {@code Spawner} class manages the introduction of new entities into the
 * active game world and the visual scene graph.
 */
public class Spawner {
    private Pane world;

    /**
     * Initializes the spawner with the target world pane.
     *
     * @param world The pane where entities should be rendered.
     */
    public void start(Pane world) {
        this.world = world;

    }

    /**
     * Spawns an entity by adding it to active lists and attaching its visual node to the world.
     *
     * @param entity The {@link Entity} to be spawned.
     */
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