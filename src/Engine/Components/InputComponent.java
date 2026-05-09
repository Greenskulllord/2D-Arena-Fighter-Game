package Engine.Components;
import Engine.Core.Component;
import Engine.Events.EventBus;
import Engine.Events.IEvent;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import Engine.Events.InputEvent;
import java.util.Queue;

/**
 * @description {@link InputComponent} is a component that is going to handle storing inputs
 * of any entity. (e.g. attack and dashing stored). works with {@link Engine.Events.InputEvent} to handle
 * detecting the inputs.
 *
 * @function an empty component with the purpose of storing over adding
 */
public class InputComponent implements Component {
    public Queue<InputEvent> queueList;

    public InputComponent() {
        queueList = new CircularFifoQueue<>(3);

    }

    @Override
    public void update(double DeltaTime) {

    }

    //helper methods
    public void consume() {
        queueList.poll();
    }

    public InputEvent peek() {
        return queueList.peek();
    }

    public void add(InputEvent event) {
        queueList.add(event);
    }

    public void clear() {
        queueList.clear();
    }

}
