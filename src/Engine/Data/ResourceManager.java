package Engine.Data;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.image.Image;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Objects;

public class ResourceManager {
    //maps that will store data instead of having the program pull from the hard drive
    //String is the file path or name (the key) and the value (like a hero sprite)
    private static final HashMap<String, JsonObject> templateCache = new HashMap<>();
    private static final HashMap<String, Image> imageCache = new HashMap<>();
    private static final HashMap<String, Image> tileCache = new HashMap<>();

    //    HashMap<String, sound> soundCache = new HashMap<>();
    //    HashMap<String, fonts> fontCache = new HashMap<>();

    //it will delete, restore and dump any extra data or save data.
    //helps with performance
    ResourceManager() {

    }


     /*
    =========================
          Cache Image
    =========================
   */

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
        catch (Exception e) {
            try {

                //save image and put into cache
                Image fallBackImage = new Image(Objects.requireNonNull(ResourceManager.class.getResourceAsStream("/EntityTextures/error_texture.png")));
                tileCache.put(path, fallBackImage);
                return fallBackImage;
                //return the fallback image, the error image

            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /*
    =========================
          Cache Tiles
    =========================
   */

    public static Image getTile(String filePath) {
        String path = filePath.startsWith("/") ? filePath : "/" + filePath;

        //if it finds key, return and load value
        if (tileCache.containsKey(path)) {
            return tileCache.get(path);
        }

        //try to add image to cache
        try (InputStream inputStream = ResourceManager.class.getResourceAsStream(path)) {
            if (inputStream == null || filePath.trim().isEmpty()) { throw new IOException("tile resources not found at: " + path); }

            Image image = new Image(inputStream);
            tileCache.put(path, image);
            return image;

        }
        catch (IOException e) {
            //error handling to return a default image
            System.out.println("====== error loading Tile ======" + "\n" + e);

            try {
                //save image and put into cache
                Image fallBackImage = new Image(Objects.requireNonNull(ResourceManager.class.getResourceAsStream("/FloorTextures/tile_test.png")));
                tileCache.put(path, fallBackImage);

                //return the fallback image, the error image
                return fallBackImage;
            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }





      /*
    =========================
         Cache Data
    =========================
   */

    public static JsonObject getData(String filePath) {
        filePath.replace("\\", "/");

        String path = filePath.startsWith("/") ? filePath : "/" + filePath;

        //if memory has it, return it
        if (templateCache.containsKey(path)) {
            return templateCache.get(path);
        }

        //if it doesn't, find it from hard drive
        JsonObject file;
        try (InputStream inputStream = ResourceManager.class.getResourceAsStream(path)) {
            if (inputStream == null || filePath.trim().isEmpty()) { throw new IOException("tile resources not found at: " + path); }

            //read the file
            InputStreamReader reader = new InputStreamReader(inputStream);

            file = JsonParser.parseReader(reader).getAsJsonObject();

            //put the template into the cache
            templateCache.put(path, file);

        } catch (FileNotFoundException e) {
            System.out.print("file could not be found:" + e);

            //if an enemy does not exist, return an invisible square
            JsonObject dummyData = new JsonObject();
            dummyData.addProperty("width", 32);
            dummyData.addProperty("height", 32);

            return dummyData;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return file;
    }

    //method to clear data
    public static void clearResources() {
        templateCache.clear();
        imageCache.clear();

    }
}
