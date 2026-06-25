package Engine.Entity;
import java.util.ArrayList;
import java.util.List;

public class EntityFactory {
    List<Class<? extends IEntityFactory>> entities = new ArrayList<>();

    //add entities in a factory queue
    public <T extends IEntityFactory> void addEntity(Class<T> entityType) {



    }

    public EntityFactory() {


    }



}
