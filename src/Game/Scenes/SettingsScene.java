package Game.Scenes;


import Engine.Managers.SceneManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.Objects;


public class SettingsScene  {
    Scene settingsMenu;


    public SettingsScene() {
        int spacing = 20;

        VBox layout = new VBox(spacing);

        layout.setStyle(
                Style.BACKGROUND_COLOR.of(Style.BACKGROUND_COLOR, Value.BLACK.getValue())
        );

        layout.setAlignment(Pos.CENTER);

        Label text = new Label("Settings will be added much later on");

        text.setStyle(
                Style.FONT_SIZE.of(Style.FONT_SIZE, Value.LARGE.getValue())
                + Style.TEXT_FILL.of(Style.TEXT_FILL, Value.WHITE.getValue())
                + Style.BACKGROUND_COLOR.of(Style.BACKGROUND_COLOR, Value.BLACK.getValue())
        );

        //button
        Button backButton = new Button("Back");

        backButton.setOnAction(e -> {
            SceneManager.SwitchScene("MAIN_MENU");
        });


        layout.getChildren().addAll(
                text,
                backButton
        );


        settingsMenu = new Scene(layout);

        //get style sheets
        settingsMenu.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ButtonStyles/menu_button.css")).toExternalForm());

        backButton.getStyleClass().add("menu-button");
    }

    public Scene GetSettingsScene() {
        return settingsMenu;
    }
}
