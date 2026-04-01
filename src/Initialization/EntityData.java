package Initialization;
import JsonComponents.JsonReader;
import com.google.gson.JsonArray;
import java.io.FileNotFoundException;

//class to hold a bunch of data during the game loop
public class EntityData {
    int width;
    int height;
    public String[] collideList;

    public EntityData() throws FileNotFoundException {
        //===================================
        //      Read from Template File
        //===================================

        JsonReader root = JsonReader.read("resources/entity_template.Json");
        JsonArray array = root.getName("Player").getIndex(0).getName("canCollideWith").raw().getAsJsonArray();
        int width = root.getName("Player").getIndex(0).getName("width").asInt();
        int height = root.getName("Player").getIndex(0).getName("height").asInt();

        //convert Json arrays to string
        String[] collideList = new String[array.size()];

        //loop through the json arrays to pull out everything
        for (int i = 0; i < array.size(); i++) {
            collideList[i] = array.get(i).getAsString();
        }

        this.registerData(width, height, collideList);
    }

    public void registerData(int width, int height, String[] collideList) {
        this.collideList = collideList;
        this.height = height;
        this.width = width;
    }
}
