package Game.Scenes;
import Engine.Managers.SceneManager;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.util.Objects;


public class MainMenuScene {
    Scene MainMenu;

    public MainMenuScene() {
        //values
        int spacing = 30;


        //the layout code
        VBox layout = new VBox(spacing);


        //nodes
        Label title = new Label("The Game");

        title.setStyle(
                Style.FONT_SIZE.of(Style.FONT_SIZE, Value.LARGE.getValue())
                + Style.TEXT_FILL.of(Style.TEXT_FILL, Value.WHITE.getValue())
        );

        //in game buttons
        Button startButton = new Button("Start");
        Button exitButton = new Button("Exit Game");
        Button settingsButton = new Button("Settings");
        Button shitButton = new Button("A Button");


        //button code
        startButton.setOnAction(e -> {
            SceneManager.SwitchScene("GAME");
        });


        exitButton.setOnAction(e -> {
            Platform.exit();
        });

        settingsButton.setOnAction(e -> {
            SceneManager.SwitchScene("SETTINGS");
        });


        //code to add nodes
        layout.getChildren().addAll(
                title,
                startButton,
                settingsButton,
                exitButton,
                shitButton
        );


        layout.setAlignment(Pos.CENTER);


        //add layout to the scene
        MainMenu = new Scene(layout);

        //load style sheet
        MainMenu.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ButtonStyles/menu_button.css")).toExternalForm());
        MainMenu.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ButtonStyles/background.css")).toExternalForm());

        //its odd why I have to do this but
        //to style buttons I have to do it like this
        layout.getStyleClass().add("background");
        startButton.getStyleClass().add("menu-button");
        exitButton.getStyleClass().add("menu-button");
        settingsButton.getStyleClass().add("menu-button");
        shitButton.getStyleClass().add("menu-button");
    }

    public Scene GetMainMenuScene() {
        return MainMenu;
    }

}
