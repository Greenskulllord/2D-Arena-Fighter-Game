package Engine.Events;
import Engine.Core.Entity;

/**
 * The {@code CollisionEvent} class carries data regarding a collision between two entities,
 * including spatial normals and timing information for resolution.
 */
public class CollisionEvent implements IEvent{
    /** The first entity involved in the collision. */
    public Entity A;

    /** The second entity involved in the collision. */
    public Entity B;

    /** The X component of the collision surface normal. */
    public double normalX;

    /** The Y component of the collision surface normal. */
    public double normalY;

    /** The specific time at which the collision occurred. */
    public double time;

    /** The time step during which the collision was detected. */
    public double DeltaTime;

    /**
     * Constructs a {@code CollisionEvent} with detailed interaction data.
     *
     * @param entityA The first participant.
     * @param entityB The second participant.
     * @param normalX The horizontal collision normal.
     * @param normalY The vertical collision normal.
     * @param time    The timestamp of impact.
     * @param DeltaTime The duration of the frame.
     */
    public CollisionEvent(Entity entityA, Entity entityB, double normalX, double normalY, double time, double DeltaTime) {
        this.A = entityA;
        this.B = entityB;
        this.normalX = normalX;
        this.normalY = normalY;
        this.time = time;
        this.DeltaTime = DeltaTime;

    }
}