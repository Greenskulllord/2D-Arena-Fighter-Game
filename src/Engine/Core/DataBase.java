package Engine.Core;
import Initialization.EntityData;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class DataBase {
    //the database
    private static final HashMap<String, EntityData> template = new HashMap<>();

    //stores data into the database
    public static void loadDatabase () throws FileNotFoundException {

        //loop to find and place info into database
        for (EntityData.EntityList list : EntityData.entityLists) {
            String entityTemplateFile = list.entityTemplateFile();
            String entityList = list.entityList();

            try {
                JsonObject root = ResourceManager.getTemplate("resources/" + entityTemplateFile); // read file
                JsonObject jsonObject = root.getAsJsonArray(entityList).get(0).getAsJsonObject();

                //all the stats to get from entityTemplateFile files
                JsonArray array = jsonObject.getAsJsonArray("canCollideWith");
                int width = jsonObject.get("width").getAsInt();
                int height = jsonObject.get("height").getAsInt();

                //convert Json arrays to string
                String[] collideList = new String[array.size()];

                //loop through the json arrays to pull out everything
                for (int k = 0; k < array.size(); k++) {
                    collideList[k] = array.get(k).getAsString();
                }

                EntityData finalData = new EntityData(width, height, collideList);

               template.put(entityList.toUpperCase(), finalData);
            }
            catch (RuntimeException e) {
                System.err.println("failed to load: " + entityList);
                e.printStackTrace();
            }
        }
    }

    //getters and setters
    public static EntityData getTemplate(String entityName) {
        return template.get(entityName);
    }
}
