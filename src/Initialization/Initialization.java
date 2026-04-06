package Initialization;
import Engine.Data.DataBase;
import Engine.Data.EntityData;
import Game.Main.Game;
import javafx.application.Application;
import java.io.FileNotFoundException;

public class Initialization {

    public static void main(String[] args) throws FileNotFoundException {
        //main is going to handle what happens at start up

        //load everything in the game
        EntityData.addToEntityList("player_template.json", "player");
        EntityData.addToEntityList("wall_template.json", "wall");
        EntityData.addToEntityList("tile_set_template.json", "tile");

        //load database
        DataBase.loadDatabase();

        //really nice that javafx has this built in method
        Application.launch(Game.class, args);
    }
}
