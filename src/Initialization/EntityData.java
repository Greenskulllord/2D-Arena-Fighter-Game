package Initialization;
import JsonComponents.JsonReader;
import com.google.gson.JsonArray;
import java.io.FileNotFoundException;
import java.util.ArrayList;

//class to hold a bunch of data during the game loop
public class EntityData {
    int width;
    int height;
    public String[] collideList;

    //a record class is a simple data carrier
    public record EntityList(String entityTemplateFile, String entityList) {};
    public static ArrayList<EntityList> entityLists = new ArrayList<>();

    public EntityData() throws FileNotFoundException {
        //===================================
        //      Read from Template File
        //===================================

        //a way to loop through the arrays and pull out the necessary data
        //to load into the database
        for (EntityList list : entityLists) {
                //get the templates
                String template = list.entityTemplateFile();
                //get the entity
                String entity = list.entityList();

                try {
                //todo - change the way file templates are obtained
                //make it not care about what '/' it uses
                //you can probably use if and else for this
                JsonReader root = JsonReader.read("resources/" + template); // read file

                //all the stats to get from template files
                JsonArray array = root.getName(entity).getIndex(0).getName("canCollideWith").raw().getAsJsonArray();
                int width = root.getName(entity).getIndex(0).getName("width").asInt();
                int height = root.getName(entity).getIndex(0).getName("height").asInt();

                //convert Json arrays to string
                String[] collideList = new String[array.size()];

                //loop through the json arrays to pull out everything
                    for (int k = 0; k < array.size(); k++) {
                        collideList[k] = array.get(k).getAsString();
                    }

                this.registerData(width, height, collideList);
            }
                catch (RuntimeException e) {
                System.err.println("failed to load: " + entity);
                e.printStackTrace();
            }
        }
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
