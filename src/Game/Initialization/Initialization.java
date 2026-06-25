package Game.Initialization;
import Engine.Data.DataBase;
import Engine.Data.DataList;
import Engine.Data.Types.EntityData;
import Game.Main.Game;
import javafx.application.Application;
import java.io.FileNotFoundException;

public class Initialization {
    public static void main(String[] args) {

        /*
        ==============================
           Load everything in game
        ==============================
        */

        //add entity templates
        DataList.addToDataList("Entities/Player/player.json", "player");

        //load database
        DataBase.loadDatabase();

        //really nice that javafx has this built in method
        Application.launch(Game.class, args);
    }
}
