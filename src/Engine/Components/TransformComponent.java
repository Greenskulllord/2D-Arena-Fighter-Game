package Engine.Components;
import Engine.Core.Component;

/**
 * The {@code TransformComponent} defines the physical position and velocity
 * of an entity in 2D space.
 */
public class TransformComponent implements Component {

    /** The current X-coordinate in world space. */
    public double x;

    /** The current Y-coordinate in world space. */
    public double y;

    /** The current horizontal velocity. */
    public double velocityX;

    /** The current vertical velocity. */
    public double velocityY;

    /**
     * Constructs a {@code TransformComponent} at a specific starting position.
     *
     * @param startX The initial world X position.
     * @param startY The initial world Y position.
     */
    public TransformComponent(double startX, double startY) {
        this.x = startX;
        this.y = startY;
    }

    @Override
    public void update(double DeltaTime) {

    }
}