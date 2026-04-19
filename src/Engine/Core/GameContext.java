package Engine.Core;

import Engine.Events.EventBus;
import Engine.System.CameraSystem;
import Engine.System.Spawner;
import Input.InputControls;
import javafx.scene.Scene;

public class GameContext {

    public final CameraSystem camera;
    public final Spawner spawner;
    public final EventBus bus;
    public final InputControls controls;
    public final Scene scene;

    public GameContext(CameraSystem camera, Spawner spawner, EventBus bus, InputControls controls, Scene scene) {
        this.bus = bus;
        this.camera = camera;
        this.controls = controls;
        this.scene = scene;
        this.spawner = spawner;

    }
}
