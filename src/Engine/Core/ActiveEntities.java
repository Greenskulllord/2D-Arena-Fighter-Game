package Engine.Core;
import java.util.ArrayList;
import java.util.List;

public class ActiveEntities {

    //create the master list
    private static List<Entity> activeEntities = new ArrayList<>();

    //the class that will fill the list with active entities and return it
    public static void fillActiveEntitiesList (Entity entity) {
        getActiveEntities().add(entity);
    }

    public static List<Entity> getActiveEntities() {
        return activeEntities;
    }
}
