package Engine.Managers;
import Engine.Components.RenderComponent;
import Engine.Core.ActiveEntities;
import Engine.Core.Entity;
import Engine.Core.GameContext;
import Engine.Core.Tile;
import Engine.Data.DataBase;
import Engine.Data.EntityData;
import Engine.System.Spawner;
import Game.Objects.Enemy;
import Game.Objects.Player;
import Game.Objects.Wall;
import Input.InputControls;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoomMapManager {

    private Stage stage;
    private Tile[] tile;
    private Pane backgroundLayer;
    private Entity[] entities;
    private GameContext context;

    //or other special entities
    private final List<Entity> playerEntities = new ArrayList<>();


    //tile manager to create rooms
    //THIS IS FOR ONLY GENERATING, NOT MAKING
    public RoomMapManager(Stage stage, Pane backgroundLayer, GameContext context) {
        this.stage = stage;
        this.backgroundLayer = backgroundLayer;
        this.context = context;

        getTile();
        getEntity();
    }

    //public void something here
    private void getTile () {
        EntityData data = DataBase.getTemplate("tile");
        Image[] bImage = data.bImage;

        try {
            //make tile array the same as there are buffered images
            tile = new Tile[bImage.length];

            for (int i = 0; i < bImage.length; i++) {
                //make a new tile at i
                tile[i] = new Tile(bImage[i]);

            }
        }
        catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    //public void something here
    private void getEntity () {
        //get data
        EntityData entityData = DataBase.getTemplate("entity"); //the entity data
        EntityData data = DataBase.getTemplate("room0"); //room data

        int[] entityMap = data.mapEntityData;
        HashMap<Integer, String> entityTemplate = entityData.entityRoomData;

        int roomSize = data.roomWidth;
        int tileSize = data.tileSize;

        try {
            //initialize the array
            entities = new Entity[entityMap.length];

            //makes a new entity based on array length
            for (int i = 0; i < entityMap.length; i++) {

                //get ID
                int ID = entityMap[i];
                if (ID == 0) continue;//null check

                //feed id to get template in hashmap
                String path = entityTemplate.get(ID);
                if (path == null) continue; //null check

                //math to position entities
                int columns = i % roomSize;
                int rows = i / roomSize;
                int spawnX = columns * tileSize;
                int spawnY = rows * tileSize;

                //case or something
                //that will spawn the entity correlated
                //to the id
                switch (ID) {
                    case 1 -> {
                        Player p = new Player(context, spawnX, spawnY, context.scene);
                        entities[i] = p;
                        this.playerEntities.add(p);
                        context.spawner.spawn(p);
                    }
                    case 2 -> {
                        Wall w = new Wall(spawnX, spawnY);
                        entities[i] = w;
                        context.spawner.spawn(w);
                    }
                    case 3 -> {
                        Enemy e = new Enemy(context, spawnX, spawnY);
                        entities[i] = e;
                        context.spawner.spawn(e);
                    }
                    default -> {
                        //do nothing
                        //prevents crash hopefully
                    }
                }
            }
        }
        catch (Exception e) {

            throw new RuntimeException(e);
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

                //hopefully gets read of white lines
                imageView.setSmooth(false);

                //math to set X, Y cords
                imageView.setX(columns * tileSize);
                imageView.setY(rows * tileSize);

                //make all images the same size as tiles size
                imageView.setFitHeight(tileSize);
                imageView.setFitWidth(tileSize);

                //add it to background layer
                backgroundLayer.getChildren().add(imageView);

                backgroundLayer.setCache(true);
                backgroundLayer.setCacheHint(CacheHint.SPEED);
            }
        }
    }

    public Entity getPlayerEntity(int playerIndex) {

        if (playerIndex >= 0 && playerIndex < playerEntities.size()){
            return playerEntities.get(playerIndex);
        }

        return null;
    }
}
