package Engine.Core;
import Initialization.EntityData;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class DataBase {
    //the database I'm pretty sure
    private static final HashMap<String, EntityData> template = new HashMap<>();

    //stores data into the database
    public static void loadDatabase () throws FileNotFoundException {
        EntityData data = new EntityData();
        //the template.put puts the moving entity into the database
        //it isn't necessary for static objects
        template.put("PLAYER", data);
    }

    //getters and setters
    public static EntityData getTemplate(String entityName) {
        return template.get(entityName);
    }
}
