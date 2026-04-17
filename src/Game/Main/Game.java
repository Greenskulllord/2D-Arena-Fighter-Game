package Game.Main;
import Engine.Components.RenderComponent;
import Engine.Core.ActiveEntities;
import Engine.Data.DataBase;
import Engine.Data.EntityData;
import Engine.Events.EventBus;
import Engine.Managers.RoomMapManager;
import Engine.System.CameraSystem;
import Engine.System.CleanUpSystem;
import Engine.System.CollisionSystem;
import Engine.Core.Entity;
import Engine.Managers.SceneManager;
import Engine.Math.DeltaTime;
import Engine.System.LifeSystem;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Input.InputControls;

import java.io.FileNotFoundException;


public class Game extends Application {

    //declare variables for scene
    Group root = new Group(); //visual elements
    Scene scene = new Scene(root, Color.GRAY); //content in stage
    InputControls controls = new InputControls();
    Pane world = new Pane();
    EventBus bus = new EventBus();

    //call the collision system
    CollisionSystem collisionSystem = new CollisionSystem(bus);
    CleanUpSystem clean = new CleanUpSystem(world);
    CameraSystem camera;
    LifeSystem lifeSystem = new LifeSystem();
    public Game() {

    }

    //block of code to run engine
    @Override
    public void start(Stage stage) {

        new SceneManager(stage);
        SceneManager.addScene("GAME", scene);//scene in stage
        SceneManager.SwitchScene("GAME");

        //load in the first room
        Pane backgroundLayer = new Pane();
        RoomMapManager mapManager = new RoomMapManager(stage, backgroundLayer, world, controls, scene);
        EntityData data = DataBase.getTemplate("room0");

        root.getChildren().addFirst(world);
        world.getChildren().addFirst(backgroundLayer);


        mapManager.generateRoom(data.mapData, data.roomWidth, data.tileSize);

        //load in camera
        camera = new CameraSystem(stage, world, mapManager.getPlayerEntity(0));
        camera.createCamera();

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
                    entity.update(deltatime); //update entities
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

            try {
                lifeSystem.update(deltatime);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            try {
                collisionSystem.update(deltatime); //update collisions
            } catch (FileNotFoundException e) {throw new RuntimeException(e);}

            for (Entity entity : ActiveEntities.getActiveEntities()) {
                entity.render(deltatime); //render entities
            }

            try {
                clean.update();
            } catch (Exception e) {throw new RuntimeException(e);}

            try {
                camera.update(deltatime); //update camera
            } catch (FileNotFoundException e) {throw new RuntimeException(e);}



        }
    };
}