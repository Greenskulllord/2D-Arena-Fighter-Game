package Initialization;
import Engine.Core.ResourceManager;
import JsonComponents.JsonReader;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;

//class to hold a bunch of data during the game loop
public class EntityData {
    public int width;
    public int height;
    public String[] collideList;

    //a record class is a simple data carrier
    public record EntityList(String entityTemplateFile, String entityList) {};
    //list containing entity templates(data) and names
    public static ArrayList<EntityList> entityLists = new ArrayList<>();

    public EntityData(int width, int height, String[] collideList) throws FileNotFoundException {
        this.collideList = collideList;
        this.width = width;
        this.height = height;

    }

    //helper method that will register all the template data
    public void registerData(int width, int height, String[] collideList) {
        this.collideList = collideList;
        this.height = height;
        this.width = width;
    }

    //helper method to add entities to the master template lists
    public static void addToEntityList(String templateFile, String entity) {
        entityLists.add(new EntityList(templateFile, entity));
    }
}
