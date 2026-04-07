package Engine.Managers;

import Engine.Core.Tile;
import Engine.Data.DataBase;
import Engine.Data.EntityData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TileMapManager {

    private Stage stage;
    private Tile[] tile;
    private Pane backgroundLayer;

    //tile manager to create rooms
    //THIS IS FOR ONLY GENERATING, NOT MAKING
    public TileMapManager(Stage stage, Pane backgroundLayer) {
        this.stage = stage;
        this.backgroundLayer = backgroundLayer;

        getTile();
    }

    //public void something here
    public void getTile () {
        EntityData data = DataBase.getTemplate("tile");
        Image[] bImage = data.bImage;

        try {
            //make tile array the same as there are buffered images
            tile = new Tile[bImage.length];

            for (int i = 0; i < bImage.length; i++) {
                //make a new tile at i
                tile[i] = new Tile();

                //make the new tile the image at i
                tile[i].image = bImage[i];

            }
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void generateRoom(int[] mapData, int roomSize, int tileSize) {
        //clear first to prevent overlapping rooms
        backgroundLayer.getChildren().clear();

        //loop through tile map data
        for (int i = 0; i < mapData.length; i++) {

            //get tile ID
            int tileID = mapData[i];

            //null check
            if (tileID == 0) continue;

            //make tileID the correct index
            int arrayID = tileID - 1;

            //null check
            if (arrayID >= 0 && arrayID < tile.length) {

                //convert to image view
                ImageView imageView = new ImageView(tile[arrayID].image);

                //math to position tiles
                int columns = i % roomSize;
                int rows = i / roomSize;

                //math to set X, Y cords
                imageView.setX(columns * tileSize);
                imageView.setY(rows * tileSize);

                //make all images the same size as tiles size
                imageView.setFitHeight(tileSize);
                imageView.setFitWidth(tileSize);

                //add it to background layer
                backgroundLayer.getChildren().add(imageView);
            }
        }
    }
}
