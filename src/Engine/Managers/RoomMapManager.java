package Engine.Managers;
import Engine.Components.RenderComponent;
import Engine.Core.ActiveEntities;
import Engine.Core.Entity;
import Engine.Core.Tile;
import Engine.Data.DataBase;
import Engine.Data.EntityData;
import Game.Objects.Player;
import Game.Objects.Wall;
import Input.InputControls;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.HashMap;

public class RoomMapManager {

    private Stage stage;
    private Tile[] tile;
    private Pane backgroundLayer;
    private Entity[] entities;

    //call components for player
    //or other special entities
    InputControls controls;

    //tile manager to create rooms
    //THIS IS FOR ONLY GENERATING, NOT MAKING
    public RoomMapManager(Stage stage, Pane backgroundLayer, Group root,  InputControls controls) {
        this.stage = stage;
        this.backgroundLayer = backgroundLayer;
        this.controls = controls;

        getTile();
        getEntity(root);
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
    private void getEntity (Group root) {
        //get data
        EntityData entityData = DataBase.getTemplate("entity"); //the entity data
        EntityData data = DataBase.getTemplate("room"); //room data

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
                        Player p = new Player(controls, spawnX, spawnY);
                        entities[i] = p;
                        SpawnEntity(p, root);
                    }
                    case 2 -> {
                        Wall w = new Wall(spawnX, spawnY);
                        entities[i] = w;
                        SpawnEntity(w, root);
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

    //spawns the entity
    public void SpawnEntity(Entity entity, Group root) {
        //add entity to list of active entities
        ActiveEntities.fillActiveEntitiesList(entity);

        //call render component to find node
        RenderComponent render = entity.getComponent(RenderComponent.class);

        //add image to entity
        if (render != null) {
            root.getChildren().add(render.getNode());
        }
    }
}
