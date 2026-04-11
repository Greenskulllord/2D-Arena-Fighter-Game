package Engine.System;


import Engine.Components.TransformComponent;
import Engine.Core.Component;
import Engine.Core.Entity;
import Engine.Managers.SceneManager;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

/*todo make a camera system for the game
    the system will use camera entities
 */
public class CameraSystem implements Component {
    Pane world;
    Stage stage;
    Pane cameraPane;
    Entity owner;

    private double cameraX = 0.0;
    private double cameraY = 0.0;
    private Boolean onStart = false;

    //might need to take in player as well so it can get
    //player X and Y and follow that
    public CameraSystem(Stage stage, Pane world, Entity owner) {
        this.stage = stage;
        this.world = world;
        this.owner = owner;

    }

    public void createCamera() {
        //camera pane
        cameraPane = new Pane();//check this out later

        //set size to stage size
        cameraPane.setPrefSize(SceneManager.WIDTH, SceneManager.HEIGHT);
    }

    @Override
    public void update(double DeltaTime) throws FileNotFoundException {
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

    //getters and setters
    public double getCameraX() {
        return cameraX;
    }

    public void setCameraX(double cameraX) {
        this.cameraX = cameraX;
    }

    public double getCameraY() {
        return cameraY;
    }

    public void setCameraY(double cameraY) {
        this.cameraY = cameraY;
    }

    public Boolean getOnStart() {
        return onStart;
    }

    public void setOnStart(Boolean onStart) {
        this.onStart = onStart;
    }
}
