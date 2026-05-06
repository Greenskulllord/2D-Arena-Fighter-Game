package Engine.Components;

import Engine.Core.Component;
import Engine.Data.DataBase;
import Engine.Data.EntityData;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code AnimationComponent} class handles the playback and management of sprite-based animations
 * for an entity. It implements the {@link Component} interface and utilizes JavaFX's
 * {@link Timeline} to cycle through a sequence of images.
 */
public class AnimationComponent implements Component {

    /** The index of the frame currently being displayed. */
    public int currentFrame;

    /** The time interval (in milliseconds) between each frame change. */
    public int frameDelay;

    /** The JavaFX {@link ImageView} node used to render the current frame. */
    public ImageView frame = new ImageView();

    /** The list of {@link Image} objects representing the sequence of the current animation state. */
    public List<Image> currentFrames = new ArrayList<>();

    Timeline timeline;
    double width;
    double height;

    /**
     * Constructs a new {@code AnimationComponent} with a specified initial state and dimensions.
     * Initializes the rendering size, sets the starting animation, and begins the playback loop.
     *
     * @param defaultState The key identifier for the initial animation state (e.g., "Idle", "Walk").
     * @param frameDelay   The duration in milliseconds to wait before switching to the next frame.
     * @param width        The width the animation images should be scaled to fit.
     * @param height       The height the animation images should be scaled to fit.
     */
    public AnimationComponent (String defaultState, int frameDelay, double width, double height) {
        this.frameDelay = frameDelay;
        this.width = width;
        this.height = height;

        frame.setFitWidth(width);
        frame.setFitHeight(height);

        //set the default animation
        changeState(defaultState);

        //creates a timeline to loop animation and play it
        timeline = new Timeline(new KeyFrame(Duration.millis(frameDelay), e -> {
            frame.setImage(currentFrames.get(currentFrame));
            currentFrame = (currentFrame + 1) % currentFrames.size();
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Switches the current animation sequence to a different state.
     * Retrieves the frame list from the {@link DataBase} using the provided state key.
     * If the state is found, the animation resets to the first frame of the new sequence.
     *
     * @param state The key name of the animation state to switch to.
     */
    public void changeState(String state) {
        EntityData data = DataBase.getTemplate(state);

        if (data == null) {
            System.out.println("error: Key Frames are currently null, check naming conventions");
            return;
        }

        currentFrames = data.keyFrames;

        //this works now
        if (!currentFrames.isEmpty()) {
            currentFrame = 0;
            frame.setImage(currentFrames.get(currentFrame));
        }

    }

    /**
     * Stops the animation {@link Timeline} and performs cleanup.
     * This should be called when the component is no longer needed to prevent memory leaks
     * or background processing.
     */
    public void destroy() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    /**
     * Updates the component logic every frame.
     * Currently, the animation timing is handled internally by the JavaFX Timeline.
     *
     * @param DeltaTime The time elapsed since the last update call.
     */
    @Override
    public void update(double DeltaTime) {


    }
}