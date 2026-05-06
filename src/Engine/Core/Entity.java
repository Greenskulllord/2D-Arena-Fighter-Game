package Engine.Core;
import Engine.Components.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Entity} class represents a generic object within the engine.
 * It acts as a container for various {@link Component} objects that define
 * its behavior and data.
 */
public abstract class Entity {

    /** The collection of all components attached to this entity. */
    public final List<Component> components = new ArrayList<>();

    /**
     * Attaches a new component to this entity.
     *
     * @param component The {@link Component} to add.
     */
    public void addComponent(Component component) {
        components.add(component);
    }

    /**
     * Iterates through all attached components and triggers their update logic.
     *
     * @param DeltaTime The time elapsed since the last update.
     */
    public void update(double DeltaTime) {
        for (Component component : components) {
            component.update(DeltaTime);
        }
    }

    /**
     * Searches for and returns the first component of the specified class type.
     *
     * @param <H>            A type extending {@link Component}.
     * @param componentClass The class object of the component to search for.
     * @return The component instance if found, or {@code null} if no match exists.
     */
    public <H extends Component> H getComponent(Class<H> componentClass) {
        //loop through all the potential components to look for
        for (int i = 0; i < components.size(); i++) {
            Component comp = components.get(i);
            //if the component is the component your looking for, do something (confusing shi I know)
            if (componentClass.isInstance(comp)) {
                //cast is apparently like, saying this generic thing is this specific thing
                return componentClass.cast(comp);
            }
        }
        //returns null if no components are found
        return null;
    }

    /**
     * Removes and returns the first component of the specified class type.
     *
     * @param <H>            A type extending {@link Component}.
     * @param componentClass The class object of the component to remove.
     * @return The removed component instance, or {@code null} if it was not found.
     */
    public <H extends Component> H removeComponent(Class<H> componentClass) {
        for (int i = 0; i < components.size(); i++) {
            Component comp = components.get(i);

            if (componentClass.isInstance(comp)) {
                components.remove(i);

                return componentClass.cast(comp);
            }
        }
        return null;
    }

    /**
     * Removes all components that are instances of the specified class type.
     *
     * @param <H>            A type extending {@link Component}.
     * @param componentClass The class object of the components to remove.
     */
    public <H extends Component> void removeAllComponents(Class<H> componentClass) {
        components.removeIf(componentClass::isInstance);
    }

    /**
     * Specialized method to handle rendering by locating a {@link RenderComponent}
     * and executing its update sequence.
     *
     * @param DeltaTime The time elapsed since the last frame.
     */
    public void render(double DeltaTime) {
        RenderComponent rc = getComponent(RenderComponent.class);
        if (rc != null) {
            rc.update(DeltaTime);
        }
    }

}