package Engine.Data;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

//class to hold a bunch of data during the game loop
public class EntityData {
    public int width;
    public int height;
    public String[] collideList;
    public Image image;


    //a record class is a simple data carrier
    public record EntityList(String entityTemplateFile, String entityList) {};
    //list containing entity templates(data) and names
    public static ArrayList<EntityList> entityLists = new ArrayList<>();

    public EntityData(int width, int height, String[] collideList) {
        this.collideList = collideList;
        this.width = width;
        this.height = height;

    }

    public EntityData(int width, int height, String[] collideList,
                      Image image, BufferedImage bufferedImage) {

        this.image = image;
        this.collideList = collideList;
        this.width = width;
        this.height = height;

    }

    //helper method to add entities to the master template lists
    public static void addToEntityList(String templateFile, String entity) {
        entityLists.add(new EntityList(templateFile, entity));
    }
}
