package Game.Main;
import Engine.Components.RenderComponent;
import Engine.Core.ActiveEntities;
import Engine.Data.DataBase;
import Engine.Data.EntityData;
import Engine.Managers.RoomMapManager;
import Engine.System.CollisionSystem;
import Engine.Core.Entity;
import Engine.Managers.SceneManager;
import Engine.Math.DeltaTime;
import Game.Objects.Player;
import Game.Objects.Wall;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import KeyboardInput.InputControls;

import java.io.FileNotFoundException;


public class Game extends Application {

    //declare variables for scene
    Group root = new Group(); //visual elements
    Scene scene = new Scene(root, Color.GRAY); //content in stage
    InputControls controls = new InputControls();

    //call the collision system
    CollisionSystem collisionSystem = new CollisionSystem();

    public Game() {

    }

    //block of code to run engine
    @Override
    public void start(Stage stage) {

        new SceneManager(stage);
        SceneManager.addScene("GAME", scene);//scene in stage
        SceneManager.SwitchScene("GAME");

        //listeners for keyboard input
        addListeners(scene);

        //load in the first room
        Pane backgroundLayer = new Pane();
        RoomMapManager mapManager = new RoomMapManager(stage, backgroundLayer, root, controls);
        EntityData data = DataBase.getTemplate("room");
        root.getChildren().addFirst(backgroundLayer);

        mapManager.generateRoom(data.mapData, data.roomWidth, data.tileSize);

        //code arguments at end of statement
        gameLoop.start(); //start loop
    }

    DeltaTime deltaTime = new DeltaTime();
    //block of code to engine update frames
    AnimationTimer gameLoop = new AnimationTimer() {
        @Override
        public void handle(long l) {

            //get deltaTime via helper method
            double deltatime = deltaTime.getDeltaTime(l);
            ActiveEntities.updateLists(); //update what's in list

            //get entities from master list
            for (Entity entity : ActiveEntities.getActiveEntities()) {
                try {
                    entity.update(deltatime);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

            try {
                collisionSystem.update(deltatime);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    };

    /*
    =======================================
                Helper Methods
    =======================================
     */

    //helper method to draw
    public void draw(GraphicsContext graphics) {}

    //add listeners so program knows when a key is being press
    public void addListeners(Scene currentScene) {
        currentScene.addEventFilter(KeyEvent.KEY_PRESSED, controls.getKeyPressedHandler());
        currentScene.addEventFilter(KeyEvent.KEY_RELEASED, controls.getKeyReleasedHandler());
    }

    //remove listeners so program doesn't when a key is being press
    public void removeListeners(Scene currentScene) {
        currentScene.removeEventFilter(KeyEvent.KEY_PRESSED, controls.getKeyPressedHandler());
        currentScene.removeEventFilter(KeyEvent.KEY_RELEASED, controls.getKeyReleasedHandler());
    }
}