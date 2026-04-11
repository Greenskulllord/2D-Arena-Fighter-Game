package Engine.Managers;
import Engine.Data.ResourceManager;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.HashMap;

//the manager to switch between scenes like "Game Over" or "Settings"
public class SceneManager {
    private static Stage window;
    private static final HashMap <String, Scene> sceneList= new HashMap<>();
    public final static double WIDTH = 1280;
    public final static double HEIGHT = 700;


    public SceneManager(Stage stage) {
       //make window the stage
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.setTitle("test");
        stage.show();
        window = stage;
    }

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

  /*
  ==================================
            Helper Methods
  ==================================
   */

    public static void addScene(String key, Scene scene) {
        sceneList.put(key, scene);
    }
}
