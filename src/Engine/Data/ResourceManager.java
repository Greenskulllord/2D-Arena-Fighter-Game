package Engine.Data;
import JsonComponents.JsonReader;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.scene.image.Image;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Objects;

public class ResourceManager {
    //maps that will store data instead of having the program pull from the hard drive
    //String is the file path or name (the key) and the value (like a hero sprite)
    private static final HashMap<String, JsonObject> templateCache = new HashMap<>();
    private static final HashMap<String, Image> imageCache = new HashMap<>();

    //    HashMap<String, sound> soundCache = new HashMap<>();
    //    HashMap<String, fonts> fontCache = new HashMap<>();

    //todo create the ResourceManager that will handle all the memory in the game
    //it will delete, restore and dump any extra data or save data.
    //helps with performance
    ResourceManager() {

    }

    //store images into cache list
    public static Image getImage(String filePath) {
        String path = filePath.startsWith("/") ? filePath : "/" + filePath;

        //if it finds key, return and load value
        if (imageCache.containsKey(path)) {
            return imageCache.get(path);
        }

        //try to add image to cache
        try (InputStream inputStream = ResourceManager.class.getResourceAsStream(path)) {
            if (inputStream == null) { throw new Exception("image resources not found at: " + path); }

            Image image = new Image(inputStream);
            imageCache.put(path, image);
            return image;

        }
        catch (Exception e){
            //error handling to return a default image
            System.out.println("====== error loading image ======" + "\n" + e);
            return new Image(Objects.requireNonNull(ResourceManager.class.getResourceAsStream("/error_texture.png")));
        }
    }

    //store the file templates into a cache list
    public static JsonObject getTemplate(String filePath) {
        //if memory has it, return it
        if (templateCache.containsKey(filePath)) {
            return templateCache.get(filePath);
        }

        //if it doesn't, find it from hard drive
        JsonObject file;
        try {
            //read the file
            JsonReader reader = JsonReader.read(filePath);
            file = reader.raw().getAsJsonObject();

            //put the template into the cache
            templateCache.put(filePath, file);

        } catch (FileNotFoundException e) {
            System.out.print("file could not be found:" + e);
            //if an enemy does not exist, return an invisible square
            JsonObject dummyData = new JsonObject();
            dummyData.addProperty("width", 32);
            dummyData.addProperty("height", 32);
            dummyData.add("canCollideWith", new JsonArray());

            return dummyData;
        }

        return file;
    }

    //method to clear data
    public static void clearResources() {
        templateCache.clear();
        imageCache.clear();

    }
}
