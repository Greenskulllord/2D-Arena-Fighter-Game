package Engine.System;
import Engine.Components.TransformComponent;
import Engine.Core.Component;
import Engine.Core.Entity;
import Engine.Managers.SceneManager;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * The {@code CameraSystem} handles world translation to simulate a camera following
 * a specific target entity.
 */
public class CameraSystem implements Component {
    Pane world;
    Stage stage;
    Pane cameraPane;
    Entity owner;

    private double cameraX = 0.0;
    private double cameraY = 0.0;
    private Boolean onStart = false;

    /**
     * Constructs a {@code CameraSystem} with a world reference and a target owner.
     *
     * @param stage The JavaFX stage.
     * @param world The root pane representing the world.
     * @param owner The entity the camera is tasked with following.
     */
    public CameraSystem(Stage stage, Pane world, Entity owner) {
        this.stage = stage;
        this.world = world;
        this.owner = owner;

    }

    /**
     * Initializes the camera pane and its dimensions.
     */
    public void createCamera() {
        //camera pane
        cameraPane = new Pane();//check this out later

        //set size to stage size
        cameraPane.setPrefSize(SceneManager.WIDTH, SceneManager.HEIGHT);
    }

    /**
     * Updates the camera position relative to the owner entity using a smoothing factor.
     *
     * @param DeltaTime The time elapsed since the last frame.
     */
    @Override
    public void update(double DeltaTime) {
        if (owner == null) return;
        //get owner location
        TransformComponent transform = owner.getComponent(TransformComponent.class);

        //get player x and y
        //this way camera knows where player is

        double playerX = transform.x;
        double playerY = transform.y;

        double screenWidth = SceneManager.WIDTH;
        double screenHeight = SceneManager.HEIGHT;

        //get target x and y
        double targetX = -playerX + (screenWidth / 2.0);
        double targetY = -playerY + (screenHeight / 2.0);

        //start on player
        if (!onStart) {
            cameraX = targetX;
            cameraY = targetY;
            onStart = true;
        }

        double smooth = 6.5; //smoothing factor

        //math to smooth it
        double smoothCameraX = cameraX + (targetX - cameraX) * smooth * DeltaTime;
        double smoothCameraY = cameraY + (targetY - cameraY) * smooth * DeltaTime;

        cameraX = smoothCameraX;
        cameraY = smoothCameraY;

        double finalCameraX = Math.floor(cameraX);
        double finalCameraY = Math.floor(cameraY);

        //make camera follow player
        world.setTranslateX(finalCameraX);
        world.setTranslateY(finalCameraY);
    }

    /**
     * Sets a new entity for the camera to follow.
     *
     * @param owner The target {@link Entity}.
     */
    public void setOwner(Entity owner) {
        this.owner = owner;
    }

    /**
     * Retrieves the current horizontal position of the camera.
     *
     * @return The camera X position.
     */
    public double getCameraX() {
        return cameraX;
    }

    /**
     * Sets the horizontal position of the camera.
     *
     * @param cameraX The new X position.
     */
    public void setCameraX(double cameraX) {
        this.cameraX = cameraX;
    }

    /**
     * Retrieves the current vertical position of the camera.
     *
     * @return The camera Y position.
     */
    public double getCameraY() {
        return cameraY;
    }

    /**
     * Sets the vertical position of the camera.
     *
     * @param cameraY The new Y position.
     */
    public void setCameraY(double cameraY) {
        this.cameraY = cameraY;
    }

    /**
     * Returns whether the camera has completed its initial positioning logic.
     *
     * @return True if initialized, false otherwise.
     */
    public Boolean getOnStart() {
        return onStart;
    }

    /**
     * Sets the initialization status of the camera.
     *
     * @param onStart The initialization status.
     */
    public void setOnStart(Boolean onStart) {
        this.onStart = onStart;
    }


}