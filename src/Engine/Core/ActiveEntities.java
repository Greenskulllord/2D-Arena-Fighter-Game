package Engine.Core;
import java.util.ArrayList;
import java.util.List;

public class ActiveEntities {

    //create the master list to handle all current active entities
    private static final List<Entity> activeEntities = new ArrayList<>();
    private static final List<Entity> waitingList = new ArrayList<>();
    private static final List<Entity> trashList = new ArrayList<>();

    //the class that will fill the list with active entities and return it
    public static void fillActiveEntitiesList (Entity entity) {
        waitingList.add(entity);
    }
    public static List<Entity> getActiveEntities() {
        return activeEntities;
    }

    public static void fillTrashList(Entity entity) {
        trashList.add(entity);
    }

    public static void updateLists() {
        activeEntities.addAll(waitingList);
        waitingList.clear();

        activeEntities.removeAll(trashList);
        trashList.clear();
        System.out.print("\nentity Successfully Deleted");
    }
}
