package Engine.Data;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

//class to hold a bunch of data during the game loop
public class EntityData {
    //entity data
    public int width;
    public int height;
    public String[] collideList;
    public Image image;

    //room data
    public Image[] bImage;
    public int[] mapData;
    public int roomWidth;
    public int roomHeight;
    public int tileSize;

    //entity room data
    public HashMap<Integer, String> entityRoomData;
    public int[] mapEntityData;


    //a record class is a simple data carrier
    public record EntityList(String entityTemplateFile, String entityList) {};
    //list containing entity templates(data) and names
    public static ArrayList<EntityList> entityLists = new ArrayList<>();

    public EntityData(
            /* Entity Data */ int width, int height, String[] collideList, Image image,

            /* Room Data */ Image[] bufferedImage, int[] mapData, int roomWidth, int roomHeight, int tileSize,

            /* Entity Room Data */ HashMap<Integer, String> entityRoomData, int[] mapEntityData
    ) {

        //entity data
        this.image = image;
        this.collideList = collideList;
        this.width = width;
        this.height = height;

        //room data
        this.bImage = bufferedImage;
        this.mapData = mapData;
        this.roomHeight = roomHeight;
        this.roomWidth = roomWidth;
        this.tileSize = tileSize;

        //entity room data
        this.entityRoomData = entityRoomData;
        this.mapEntityData = mapEntityData;

    }

    //helper method to add entities to the master template lists
    public static void addToEntityList(String templateFile, String entity) {
        entityLists.add(new EntityList(templateFile, entity));
    }
}
