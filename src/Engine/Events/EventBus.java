package Engine.Events;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A central, deferred Event Bus that facilitates decoupled communication between systems.
 * <p>
 * This class uses the Publish-Subscribe pattern. Systems can subscribe to specific
 * event types, and other systems can publish events to be processed later. Events
 * are queued and only resolved when {@link #dispatchEvents()} is explicitly called,
 * ensuring stable execution during the game loop.
 */
public class EventBus {

    //list that will contain all events needed to be queued
    @SuppressWarnings("unchecked")
    private final Map<Class<? extends IEvent>, List<IEvent.IEventListener<?>>> listeners;
    private final List<IEvent> eventQueue;

    /**
     * Constructs a new EventBus, initializing the routing table and the event queue.
     */
    public EventBus() {
        this.listeners = new HashMap<>();
        this.eventQueue = new LinkedList<>();
    }

    /**
     * Registers a listener to be notified whenever a specific type of event is published.
     *
     * @param eventType The class blueprint of the event to listen for (e.g., CollisionEvent.class).
     * @param Listener  The system or object that wants to receive the event.
     * @param <T>       The generic type ensuring the listener matches the event type.
     */
    public <T extends IEvent> void subscribeEvent(Class<T> eventType, IEvent.IEventListener<T> Listener) {
        //if null, create the event type, create an array for it and add the listener
        listeners.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>()).add(Listener);
    }

    /**
     * Removes a listener from the routing table.
     * <p>
     * It is critical to call this when an entity or system is destroyed to prevent memory leaks.
     *
     * @param eventType The class blueprint of the event the listener was subscribed to.
     * @param Listener  The system or object to be removed.
     * @param <T>       The generic type ensuring the listener matches the event type.
     */
    public <T extends IEvent> void unSubscribeEvent(Class<T> eventType, IEvent.IEventListener<T> Listener)  {
        //remove the events and listeners
        List<IEvent.IEventListener<?>> eventListeners = listeners.get(eventType);
        if (eventListeners != null) {
            eventListeners.remove(Listener); //remove them
        }
    }

    /**
     * Adds an event to the queue to be processed later.
     * <p>
     * This method is thread-safe. The event logic will not execute immediately;
     * it waits until {@link #dispatchEvents()} is called.
     *
     * @param event The initialized event object containing relevant data.
     */
    public void publishEvent(IEvent event) {
        synchronized (eventQueue) {


            //add an event to the queue
            eventQueue.add(event);
        }
    }

    /**
     * Processes and clears all events currently sitting in the queue.
     * <p>
     * This method iterates through the waiting events, looks up the subscribed listeners
     * in the routing table, and triggers their respective logic. This should typically
     * be called exactly once at the end of the main game loop.
     */
    public void dispatchEvents() {
        if (eventQueue.isEmpty()) return;

        Queue<IEvent> queueList = new LinkedList<>(eventQueue);//get an event
        eventQueue.clear(); //clear event

        while (!queueList.isEmpty()) {
            IEvent event = queueList.poll(); //retrieve head
            Class<? extends IEvent> eventType = event.getClass(); //get eventType
            List<IEvent.IEventListener<?>> eventListeners = listeners.get(eventType); //get listeners attached

            if (eventListeners != null) {
                for (IEvent.IEventListener listener: eventListeners) listener.onEvent(event);
            }

        }

    }
}
