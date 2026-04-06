package Engine.Managers;

import Engine.Core.Tile;
import Engine.Data.DataBase;
import Engine.Data.EntityData;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class TileMapManager {

    private Stage stage;
    private Tile[] tile;


    //tile manager to create rooms
    //THIS IS FOR ONLY GENERATING, NOT MAKING
    TileMapManager(Stage stage) {
        this.stage = stage;

        //make the tiles - number is tile set amount
        tile = new Tile[20];

        getTile();
    }

    //public void something here
    public void getTile () {
        EntityData data = DataBase.getTemplate("tile");

        try {

            //for loop to make new tiles for event item in array
            for (int i = 0; i < tile.length; i++) {
                tile[i] = new Tile();
            }

            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("")));


        }
        catch (Exception e) {

            e.printStackTrace();
        }






    }

}
