package Engine.Components;

import Engine.Core.Component;
import javax.swing.*;

/**
 * The {@code CollisionComponent} class defines a rectangular bounding box used for
 * collision detection within the engine. It calculates boundaries based on a
 * {@link TransformComponent} and specific offset values.
 */
public class CollisionComponent implements Component {

    /** The vertical extent of the collision hitbox. */
    public double height;

    /** The horizontal extent of the collision hitbox. */
    public double width;

    double offsetX;
    double offsetY;
    TransformComponent transformComponent;

    /** The broad classification group for this collision (e.g., "Solid", "Trigger"). */
    public String category;

    /** The specific identifier for the object type (e.g., "Player", "Enemy", "Wall"). */
    public String type;

    /**
     * Constructs a {@code CollisionComponent} with defined dimensions, offsets, and type data.
     *
     * @param Width     The width of the hitbox.
     * @param Height    The height of the hitbox.
     * @param offsetX   The X-axis offset relative to the transform position.
     * @param offsetY   The Y-axis offset relative to the transform position.
     * @param transform The {@link TransformComponent} providing the base coordinates.
     * @param category  The collision category for filtering.
     * @param type      The specific type of the colliding entity.
     */
    public CollisionComponent(double Width, double Height,
                              double offsetX, double offsetY, TransformComponent transform, String category, String type) {

        this.category = category;
        this.type = type;
        this.transformComponent = transform;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.height = Height;
        this.width = Width;

    }

    /**
     * Updates the component logic.
     *
     * @param DeltaTime The time elapsed since the last update call.
     */
    @Override
    public void update(double DeltaTime) {
    }

    /*
    =========================
        helper methods
    =========================
     */

    /**
     * Calculates the leftmost X-coordinate of the hitbox.
     * @return The world X position plus the horizontal offset.
     */
    public double getMinX() {
        return transformComponent.x + offsetX;
    }

    /**
     * Calculates the topmost Y-coordinate of the hitbox.
     * @return The world Y position plus the vertical offset.
     */
    public double getMinY() {
        return transformComponent.y + offsetY;
    }

    /**
     * Calculates the rightmost X-coordinate of the hitbox.
     * @return The world X position plus offset and width.
     */
    public double getMaxX() {
        return transformComponent.x + offsetX + width;
    }

    /**
     * Calculates the bottommost Y-coordinate of the hitbox.
     * @return The world Y position plus offset and height.
     */
    public double getMaxY() {
        return transformComponent.y + offsetY + height;
    }

}