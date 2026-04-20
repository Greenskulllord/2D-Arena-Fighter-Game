package Game.Main;
import Engine.Core.ActiveEntities;
import Engine.Core.GameContext;
import Engine.Data.DataBase;
import Engine.Data.EntityData;
import Engine.Events.CollisionEvent;
import Engine.Events.EventBus;
import Engine.Managers.RoomMapManager;
import Engine.System.*;
import Engine.Core.Entity;
import Engine.Managers.SceneManager;
import Engine.Math.DeltaTime;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Input.InputControls;


public class Game extends Application {

    //declare variables for scene
    Group root = new Group(); //visual elements
    Scene scene = new Scene(root, Color.GRAY); //content in stage
    InputControls controls = new InputControls();
    EventBus bus = new EventBus();
    Pane world = new Pane();
    Pane backgroundLayer = new Pane();

    //call the collision system
    CollisionSystem collisionSystem = new CollisionSystem(bus);
    CleanUpSystem clean = new CleanUpSystem(world);
    CameraSystem camera;
    LifeSystem lifeSystem = new LifeSystem();
    Spawner spawner = new Spawner();

    CombatSystem combat = new CombatSystem();
    MovementConst move = new MovementConst(bus);
    MovementSystem moveSystem = new MovementSystem();

    //block of code to run engine
    @Override
    public void start(Stage stage) {
        //make scene and set it
        new SceneManager(stage);
        SceneManager.addScene("GAME", scene);//scene in stage
        SceneManager.SwitchScene("GAME");

        //add the children
        root.getChildren().addFirst(world);
        world.getChildren().addFirst(backgroundLayer);

        //load in camera
        camera = new CameraSystem(stage, world, null);
        camera.createCamera();
        spawner.start(world);

        //add context on start
        GameContext context = new GameContext(camera, spawner, bus, controls, scene);

        //load in the first room
        RoomMapManager mapManager = new RoomMapManager(stage, backgroundLayer, context);
        EntityData data = DataBase.getTemplate("room0");
        mapManager.generateRoom(data.mapData, data.roomWidth, data.tileSize);

        //set owner of camera
        camera.setOwner(mapManager.getPlayerEntity(0));

        //load in events
        bus.subscribeEvent(CollisionEvent.class, combat::onCollision);

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
                entity.update(deltatime);//update entities
            }

            collisionSystem.update(deltatime);//update collisions
            bus.dispatchEvents();
            moveSystem.update(deltatime);

            for (Entity entity : ActiveEntities.getActiveEntities()) {
                entity.render(deltatime); //render entities
            }

            lifeSystem.update(deltatime);
            clean.update();
            camera.update(deltatime); //update camera

        }
    };
}