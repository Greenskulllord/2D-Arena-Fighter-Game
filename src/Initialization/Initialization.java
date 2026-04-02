package Initialization;
import Engine.Core.DataBase;
import Game.Game;
import JsonComponents.JsonReader;
import com.google.gson.JsonArray;
import javafx.application.Application;
import java.io.FileNotFoundException;
import java.util.*;

public class Initialization {

    public static void main(String[] args) throws FileNotFoundException {
        //main is going to handle what happens at start up

        //load everything in the game
        EntityData.addToEntityList("player_template.json", "player");

        //load database
        DataBase.loadDatabase();

        //really nice that javafx has this built in method
        Application.launch(Game.class, args);
    }
}
