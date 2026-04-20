package Engine.Events;
import Engine.Core.Entity;

public class CollisionEvent implements IEvent{
    public Entity A;
    public Entity B;
    public double normalX;
    public double normalY;
    public double time;
    public double DeltaTime;

    public CollisionEvent(Entity entityA, Entity entityB, double normalX, double normalY, double time, double DeltaTime) {
        this.A = entityA;
        this.B = entityB;
        this.normalX = normalX;
        this.normalY = normalY;
        this.time = time;
        this.DeltaTime = DeltaTime;

    }
}
