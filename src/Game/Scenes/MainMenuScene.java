package Game.Scenes;
import javafx.geometry.Pos;
import javafx.scene.Group;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.awt.*;


public class MainMenuScene {
    Scene MainMenu;


    private Group root;

    private Pane basePane;

    public MainMenuScene() {
        //values
        int spacing = 20;


        //the layout code
        VBox layout = new VBox(spacing);
        layout.setStyle(
                Style.BACKGROUND_COLOR.of(Style.BACKGROUND_COLOR, "black")

        );


        //nodes
        Label title = new Label("The Game");

        title.setStyle(
                Style.FONT_SIZE.of(Style.FONT_SIZE, Value.LARGE.getValue())
                        + Style.TEXT_FILL.of(Style.TEXT_FILL, Value.WHITE.getValue())
        );

        //in game buttons
        Button startButton = new Button("Start");
        Button exitButton = new Button("Exit Game");


        //code to add nodes
        layout.getChildren().addAll(title, startButton, exitButton);
        layout.setAlignment(Pos.CENTER);


        //add layout to the scene
        MainMenu = new Scene(layout);

    }

    public Scene GetMainMenuScene() {
        return MainMenu;
    }

}
