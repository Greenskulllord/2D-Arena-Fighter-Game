package Engine.Events;
import Engine.Core.Entity;

public class CollisionEvent implements IEvent{
    public final Entity A;
    public final Entity B;
    public final double normalX;
    public final double normalY;

    public CollisionEvent(Entity entityA, Entity entityB, double normalX, double normalY) {
        this.A = entityA;
        this.B = entityB;
        this.normalX = normalX;
        this.normalY = normalY;
    }
}
