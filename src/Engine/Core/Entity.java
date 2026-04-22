package Engine.Core;
import Engine.Components.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

//these are my entities
public abstract class Entity {
    //make a list for the components
    //todo instead of list, change to map for more speed
    public final List<Component> components = new ArrayList<>();

    //a method to add components
    public void addComponent(Component component) {
        components.add(component);
    }

    //a method to update components every 60 frames
    public void update(double DeltaTime) {
        for (Component component : components) {
            component.update(DeltaTime);
        }
    }

    //helper method to look for components

    //placeholder variable will be H
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

    public <H extends Component> void removeAllComponents(Class<H> componentClass) {
        components.removeIf(componentClass::isInstance);
    }

    public void render(double DeltaTime) {
        RenderComponent rc = getComponent(RenderComponent.class);
        if (rc != null) {
            rc.update(DeltaTime);
        }
    }

}
