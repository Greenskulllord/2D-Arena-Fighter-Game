package Game;
import Engine.Components.CollisionComponent;
import Engine.Components.TransformComponent;
import Engine.Core.ActiveEntities;
import Engine.Core.CollisionSystem;
import Engine.Core.Entity;
import Engine.Math.DeltaTime;
import Game.Objects.Player;
import Game.Objects.Wall;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import KeyboardInput.InputControls;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Game extends Application {


    //set width and height for screen
    private final int WIDTH = 600;
    private final int HEIGHT = 400;

    //declare variables for scene
    Group root = new Group(); //visual elements
    Scene scene = new Scene(root, Color.BLACK); //content in stage
    InputControls controls = new InputControls();

    //declare variables for objects
    Player player = new Player(controls);
    Wall wall = new Wall();

    //call the collision system
    CollisionSystem collisionSystem = new CollisionSystem();

    public Game() throws FileNotFoundException {
    }

    //block of code to run engine
    @Override
    public void start(Stage stage) {
        //listeners for keyboard input
        addListeners();

        //windows properties
        stage.setScene(scene); //scene in stage
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.setTitle("test");

        //add player
        root.getChildren().add(player.getNode());
        root.getChildren().add(wall.getNode());

        //code arguments at end of statement
        gameLoop.start(); //start loop
        stage.show(); //show window
        root.requestFocus(); //focus
    }

    DeltaTime deltaTime = new DeltaTime();
    //block of code to engine update frames
    AnimationTimer gameLoop = new AnimationTimer() {
        @Override
        public void handle(long l) {
            //get deltaTime via helper method
            double deltatime = deltaTime.getDeltaTime(l);

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

    //helper method to draw
    public void draw(GraphicsContext graphics) {}

    //add listeners so program knows when a key is being press
    public void addListeners() {
        assert scene != null;
        scene.addEventFilter(KeyEvent.KEY_PRESSED, controls.getKeyPressedHandler());
        scene.addEventFilter(KeyEvent.KEY_RELEASED, controls.getKeyReleasedHandler());
    }

    //remove listeners so program doesn't when a key is being press
    public void removeListeners() {
        scene.removeEventFilter(KeyEvent.KEY_PRESSED, controls.getKeyPressedHandler());
        scene.removeEventFilter(KeyEvent.KEY_RELEASED, controls.getKeyReleasedHandler());
    }
}