package Engine.Data;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBase {
    //the database
    private static final HashMap<String, EntityData> template = new HashMap<>();

    //stores data into the database
    public static void loadDatabase () {

        //loop to find and place info into database
        for (EntityData.EntityList list : EntityData.entityLists) {
            String entityTemplateFile = list.entityTemplateFile();
            String entityList = list.entityList();

            try {
                //get main json templates
                JsonObject root = ResourceManager.getTemplate("resources/" + entityTemplateFile); // read file

                //check if root is null or not
                if (!root.has(entityList) || root.get(entityList).isJsonNull()) {

                    System.out.println("System Error: Entity List " + entityList + " is missing");
                    continue;
                }

                //null check for json array
                JsonArray jsonArray = root.getAsJsonArray(entityList);
                if (jsonArray.isEmpty()) continue;

                //get the array finally
                JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();

                /*
                =================
                   Get Images
                =================
                 */

                //get the sprite image
                Image image = null;

                if (jsonObject.has("image") && !jsonObject.get("image").isJsonNull()) {

                    String imagePath = jsonObject.get("image").getAsString();
                    image = ResourceManager.getImage(imagePath);
                } else System.out.println("Warning: image is null in " + jsonObject);

                   /*
                =========================
                      Buffered Tiles
                =========================
                 */

                //make a map for tiles
                List<Image> entityTiles = new ArrayList<>();

                //null check if there's no tile
                if (entityList.equalsIgnoreCase("tile")) {

                        //for loop to get tiles all tiles
                        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                            //get path
                            String bImagePath = entry.getValue().getAsString();

                            //null check for empty string
                            if (bImagePath != null && !bImagePath.trim().isEmpty()) {
                                Image buffImage = ResourceManager.getTile(bImagePath);
                                entityTiles.add(buffImage);

                            } else System.out.println("\nWarning" + bImagePath + " is null");
                        }
                } else System.out.println("\nWarning: tile is null in " + jsonObject);

                //put images into their own array
                Image[] finalBuffImages = entityTiles.toArray(new Image[0]);

                /*
                =============================
                    Stats from templates
                =============================
                 */

                //the width and height
                int width = jsonObject.has("width") && !jsonObject.get("width").isJsonNull()
                        ? jsonObject.get("width").getAsInt() : 0;


                int height = jsonObject.has("height") && !jsonObject.get("height").isJsonNull()
                        ? jsonObject.get("height").getAsInt() : 0;


                //convert Json arrays to string
                String[] collideList = new String[0];

                 /*
                =============================
                       Collision Stats
                =============================
                 */

                //null check if no array
                if (jsonObject.has("canCollideWith") && !jsonObject.get("canCollideWith").isJsonNull()) {
                    //what it can collide with
                    JsonArray array = jsonObject.getAsJsonArray("canCollideWith");
                    collideList = new String[array.size()];

                    //loop through the json arrays to pull out everything
                    for (int k = 0; k < array.size(); k++) {

                        collideList[k] = array.get(k).getAsString();
                    }
                } else System.out.println("\nWarning: Field canCollideWith is null in " + jsonObject);

                /*
                =========================
                      Room Data
                =========================
                 */

                //the width and height of room
                //also get tile size
                int roomWidth = root.has("roomWidth") && !root.get("roomWidth").isJsonNull()
                        ? root.get("roomWidth").getAsInt() : 0;


                int roomHeight = root.has("roomHeight") && !root.get("roomHeight").isJsonNull()
                        ? root.get("roomHeight").getAsInt() : 0;

                int tileSize = root.has("tileSize") && !root.get("tileSize").isJsonNull()
                        ? root.get("tileSize").getAsInt() : 0;


                //initialize the 1D array
                int[] dataArray = new int[0];

                //null check
                if (root.has("room") && !root.get("room").isJsonNull()) {
                    JsonArray layer = root.getAsJsonArray("room");

                    //null check
                    if (!layer.isEmpty()) {

                        JsonObject roomData = layer.get(0).getAsJsonObject();
                        //null check
                        if (roomData.has("data") && !roomData.get("data").isJsonNull()) {

                            JsonArray data = roomData.getAsJsonArray("data");
                            //add to 1D array
                            dataArray = new int[data.size()];

                            for (int i = 0; i < dataArray.length; i++) {
                                //fill the 1D int array
                                dataArray[i] = data.get(i).getAsInt();

                            }
                        } else System.out.println("\nWarning: " + roomData + " is null");
                    } else System.out.println("\nWarning: " + layer + " is empty");
                } else System.out.println("\nWarning: layer is null in " + jsonObject);




                EntityData finalData = new EntityData(
                        /* Entity Data */ width, height, collideList, image,

                        /* Room Data */ finalBuffImages, dataArray, roomWidth, roomHeight, tileSize

                        /* */
                );

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
        return template.get(entityName.toUpperCase());
    }
}
