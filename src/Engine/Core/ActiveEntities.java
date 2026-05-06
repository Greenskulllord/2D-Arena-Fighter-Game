package Engine.Core;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code ActiveEntities} class manages the lifecycle of all entities within the engine.
 * It utilizes a deferred buffering system (waiting and trash lists) to safely add and
 * remove entities without causing {@link java.util.ConcurrentModificationException}
 * during the game loop.
 */
public class ActiveEntities {

    //create the master list to handle all current active entities
    private static final List<Entity> activeEntities = new ArrayList<>();
    private static final List<Entity> waitingList = new ArrayList<>();
    private static final List<Entity> trashList = new ArrayList<>();

    /**
     * Queues an entity to be added to the active list during the next update cycle.
     *
     * @param entity The {@link Entity} to be activated.
     */
    public static void fillActiveEntitiesList (Entity entity) {
        waitingList.add(entity);
    }

    /**
     * Retrieves the current list of active entities.
     *
     * @return A {@link List} of all {@link Entity} objects currently in the simulation.
     */
    public static List<Entity> getActiveEntities() {
        return activeEntities;
    }

    /**
     * Queues an entity to be removed from the active list during the next update cycle.
     *
     * @param entity The {@link Entity} to be decommissioned.
     */
    public static void fillTrashList(Entity entity) {
        trashList.add(entity);
    }

    /**
     * Synchronizes the entity lists by moving queued entities from the waiting list
     * to the active list and removing entities flagged in the trash list.
     */
    public static void updateLists() {
        activeEntities.addAll(waitingList);
        waitingList.clear();

        activeEntities.removeAll(trashList);
        trashList.clear();
    }
}