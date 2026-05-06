package Engine.Core;

/**
 * The {@code Component} interface serves as the base for all data and logic modules
 * that can be attached to an {@link Entity}.
 */
public interface Component {

    /**
     * Updates the component state based on the time elapsed since the last frame.
     *
     * @param DeltaTime The time difference in seconds (or milliseconds) between frames.
     */
    public void update(double DeltaTime);
}