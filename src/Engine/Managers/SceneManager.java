package Engine.Managers;
import Engine.Data.ResourceManager;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.HashMap;

/**
 * The {@code SceneManager} class is responsible for managing different game states
 * by switching between various JavaFX scenes.
 */
public class SceneManager {
    private static Stage window;
    private static final HashMap <String, Scene> sceneList= new HashMap<>();

    /** The default width for scenes managed by this manager. */
    public final static double WIDTH = 1280;
    /** The default height for scenes managed by this manager. */
    public final static double HEIGHT = 700;

    /**
     * Initializes the {@code SceneManager} with a primary stage.
     *
     * @param stage The primary JavaFX stage to be used for scene display.
     */
    public SceneManager(Stage stage) {
        //make window the stage
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.setTitle("test");
        stage.show();
        window = stage;
    }

    /**
     * Switches the current display to the scene associated with the provided name.
     *
     * @param sceneName The identifier for the scene to be loaded.
     */
    public static void SwitchScene(String sceneName) {
        try {
            if (sceneList.containsKey(sceneName)) {
                ResourceManager.clearResources(); //clear ram
                window.setScene(sceneList.get(sceneName)); //switch windows

                window.show(); //show window
            }
        }
        catch (Exception e) {
            System.out.println("Failed to load scene: " + sceneName);

        }
    }

    /**
     * Registers a new scene with the manager.
     *
     * @param key   The unique string identifier for the scene.
     * @param scene The {@link Scene} object to store.
     */
    public static void addScene(String key, Scene scene) {
        sceneList.put(key, scene);
    }
}