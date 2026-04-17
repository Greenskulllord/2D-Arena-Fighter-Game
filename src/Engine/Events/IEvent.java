package Engine.Events;

//source: https://dzone.com/articles/design-patterns-event-bus


/**
 * interface describing a generic event, and it's associated meta data, it's this what's going to
 * get sent in the bus to be dispatched to intrested Subscribers
 */
public interface IEvent {
    /**
     * @returns the stored data associated with the event
     */
    interface IEventListener<T extends IEvent> {
        void onEvent(T event);
    }
}
