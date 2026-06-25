package Engine.Data;
import Engine.Data.Types.AnimationData;
import Engine.Data.Types.CollisionData;
import Engine.Data.Types.EntityData;
import Engine.Data.Types.RoomData;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;

public class DataBase {
    //the database
    private static final HashMap<String, EntityData> ENTITY_DATA_HASH_MAP = new HashMap<>();
    private static final HashMap<String, RoomData> ROOM_DATA_HASH_MAP = new HashMap<>();
    private static final HashMap<String, CollisionData> COLLISION_DATA_HASH_MAP = new HashMap<>();
    private static final HashMap<String, AnimationData> ANIMATION_DATA_HASH_MAP = new HashMap<>();

    //stores data into the database
    public static void loadDatabase() {
        Gson gson = new Gson();

        //loop through every file it sees in the list
        for (DataList.data dataList : DataList.dataList) {
            String file = dataList.file();
            String type = dataList.dataType();
            JsonObject path = ResourceManager.getData(file);

            if (path == null || path.isEmpty()) {
                System.out.print("error loading file: " + path + "\n");
                continue;
            }

            switch (type) {
                case "entity":


                    break;


                default:
                    System.err.println("unknown data type: " + type + "\n");
                    break;
            }

            //turns all the data in objects
            EntityData entityData = gson.fromJson(ResourceManager.getData(file), EntityData.class);
            AnimationData animationData = gson.fromJson(ResourceManager.getData(file), AnimationData.class);
            CollisionData collisionData = gson.fromJson(ResourceManager.getData(file), CollisionData.class);
            RoomData roomData = gson.fromJson(ResourceManager.getData(file), RoomData.class);


            ENTITY_DATA_HASH_MAP.put(type.toUpperCase(), entityData);
            ROOM_DATA_HASH_MAP.put(type.toUpperCase(), roomData);
            COLLISION_DATA_HASH_MAP.put(type.toUpperCase(), collisionData);
            ANIMATION_DATA_HASH_MAP.put(type.toUpperCase(), animationData);
        }



        //loop to find and place info into database
//        for (EntityData.EntityList list : EntityData.entityLists) {
//            String entityTemplateFile = list.entityTemplateFile();
//            String entityList = list.entityList();
//
//            try {
//                //get main json templates
//                JsonObject root = ResourceManager.getData(entityTemplateFile); // read file
//kmi
//                //check if root is null or not
//                if (!root.has(entityList) || root.get(entityList).isJsonNull()) {
//
//                    System.out.println("System Error: Entity List " + entityList + " is missing");
//                    continue;
//                }
//
//                //null check for json array
//                JsonArray jsonArray = root.getAsJsonArray(entityList);
//                if (jsonArray.isEmpty()) continue;
//
//                //get the array finally
//                JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
//
//                /*
//                ================= ========================= ========================= =========================
//                   Get Images
//                ================= ========================= ========================= =========================
//                 */
//
//                //get the sprite image
//                Image image = null;
//
//                if (jsonObject.has("image") && !jsonObject.get("image").isJsonNull()) {
//
//                    String imagePath = jsonObject.get("image").getAsString();
//                    image = ResourceManager.getImage(imagePath);
//                }
//
//
//
//
//                List<Image> keyFrames = new ArrayList<>();
//
//                //get animation images
//                if (jsonObject.has("keyFrames") && !jsonObject.get("keyFrames").isJsonNull()) {
//                    JsonArray keyFrameArray = jsonObject.getAsJsonArray("keyFrames");
//
//                    if (!keyFrameArray.isEmpty()) {
//                        for (JsonElement frames : keyFrameArray) {
//                            String frame = frames.getAsString();
//                            image = ResourceManager.getImage(frame);
//
//                            keyFrames.add(image);
//                        }
//                    }
//                }
//
//
//
//                /*
//                ========================= ========================= ========================= =========================
//                      Buffered Tiles
//                ========================= ========================= ========================= =========================
//                 */
//
//                //make a map for tiles
//                List<Image> entityTiles = new ArrayList<>();
//
//                //null check if there's no tile
//                if (entityList.equalsIgnoreCase("tile")) {
//
//                        //for loop to get tiles all tiles
//                        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
//                            //get path
//                            String bImagePath = entry.getValue().getAsString();
//
//                            //null check for empty string
//                            if (bImagePath != null && !bImagePath.trim().isEmpty()) {
//                                Image buffImage = ResourceManager.getTile(bImagePath);
//                                entityTiles.add(buffImage);
//
//                            }
//                        }
//                }
//
//                //put images into their own array
//                Image[] finalBuffImages = entityTiles.toArray(new Image[0]);
//
//
//
//
//
//                /*
//                ============================= ========================= ========================= =========================
//                    Stats from templates
//                ============================= ========================= ========================= =========================
//                 */
//
//                //the width and height
//                int width = jsonObject.has("width") && !jsonObject.get("width").isJsonNull()
//                        ? jsonObject.get("width").getAsInt() : 0;
//
//                int height = jsonObject.has("height") && !jsonObject.get("height").isJsonNull()
//                        ? jsonObject.get("height").getAsInt() : 0;
//
//                int health = jsonObject.has("health") && !jsonObject.get("health").isJsonNull()
//                        ? jsonObject.get("health").getAsInt() : 0;
//
//                int damage = jsonObject.has("damage") && !jsonObject.get("damage").isJsonNull()
//                        ? jsonObject.get("damage").getAsInt() : 0;
//
//                int maxHealth = jsonObject.has("max_health") && !jsonObject.get("max_health").isJsonNull()
//                        ? jsonObject.get("max_health").getAsInt() : 0;
//
//                double speed = jsonObject.has("speed") && !jsonObject.get("speed").isJsonNull()
//                        ? jsonObject.get("speed").getAsDouble() : 0;
//
//                double attackSpeed = jsonObject.has("attackSpeed") && !jsonObject.get("attackSpeed").isJsonNull()
//                        ? jsonObject.get("attackSpeed").getAsDouble() : 0;
//
//                double attackForce = jsonObject.has("attackForce") && !jsonObject.get("attackForce").isJsonNull()
//                        ? jsonObject.get("attackForce").getAsInt() : 0;
//
//                double dashSpeed = jsonObject.has("dashSpeed") && !jsonObject.get("dashSpeed").isJsonNull()
//                        ? jsonObject.get("dashSpeed").getAsDouble() : 0;
//
//                double dashDuration = jsonObject.has("dashDuration") && !jsonObject.get("dashDuration").isJsonNull()
//                        ? jsonObject.get("dashDuration").getAsDouble() : 0;
//
//
//
//
//                 /*
//                ============================= ========================= ========================= =========================
//                       Collision Stats
//                ============================= ========================= ========================= =========================
//                 */
//
//                String type = jsonObject.has("type") && !jsonObject.get("type").isJsonNull()
//                        ? jsonObject.get("type").getAsString() : "NONE";
//
//                String category = jsonObject.has("category") && !jsonObject.get("category").isJsonNull()
//                        ? jsonObject.get("category").getAsString() : "NONE";
//
//                //convert Json arrays to string
//                String[] collideList = new String[0];
//
//                //null check if no array
//                if (jsonObject.has("canCollideWith") && !jsonObject.get("canCollideWith").isJsonNull()) {
//                    //what it can collide with
//                    JsonArray array = jsonObject.getAsJsonArray("canCollideWith");
//                    collideList = new String[array.size()];
//
//                    //loop through the json arrays to pull out everything
//                    for (int k = 0; k < array.size(); k++) {
//
//                        collideList[k] = array.get(k).getAsString();
//                    }
//                }
//
//
//
//                  /*
//                ========================= ========================= ========================= =========================
//                    Entity Room Data
//                ========================= ========================= ========================= =========================
//                 */
//
//                HashMap<Integer, String> entityRoomData = new HashMap<>();
//
//                //get string path to template
//                if (entityList.equalsIgnoreCase("entity")) {
//
//                        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
//                            //get key ID and string path
//                            String key = entry.getKey();
//                            String path = entry.getValue().getAsString();
//
//                            try {
//                                //parse string to int
//                                int entityID = Integer.parseInt(key);
//                                entityRoomData.put(entityID, path); //put in data
//
//                            }
//                            catch (Exception e) {
//                                System.out.print("\nissue with template json file or Room json file ");
//                                throw new RuntimeException(e);
//                            }
//                        }
//                }
//
//                /*
//                ========================= ========================= ========================= =========================
//                      Room Data
//                ========================= ========================= ========================= =========================
//                 */
//
//                //the width and height of room
//                //also get tile size
//                int roomWidth = root.has("mapWidth") && !root.get("mapWidth").isJsonNull()
//                        ? root.get("mapWidth").getAsInt() : 0;
//
//                int roomHeight = root.has("mapHeight") && !root.get("mapHeight").isJsonNull()
//                        ? root.get("mapHeight").getAsInt() : 0;
//
//                int tileSize = root.has("tileSize") && !root.get("tileSize").isJsonNull()
//                        ? root.get("tileSize").getAsInt() : 0;
//
//
//                //initialize the 1D array for room tile data
//                int[] dataArray = new int[0];
//
//                //initialize the 1D array for room entity data
//                int[] entityDataArray = new int[0];
//
//                //for loop for every room
//                for (Map.Entry<String, JsonElement> entry : root.entrySet()) {
//
//                    String roomKey = entry.getKey();
//
//                    //null check
//                    if (roomKey.startsWith("room") && !entry.getValue().isJsonNull()) {
//
//                        JsonArray layer = entry.getValue().getAsJsonArray();
//
//                        //null check
//                        if (!layer.isEmpty()) {
//
//                            JsonObject roomData = layer.get(0).getAsJsonObject();
//                            JsonObject roomEntityData = layer.get(1).getAsJsonObject();
//
//                            //null check
//                            if (roomData.has("data") && !roomData.get("data").isJsonNull()) {
//
//                                JsonArray data = roomData.getAsJsonArray("data");
//                                JsonArray entityData = roomEntityData.getAsJsonArray("spawnData");
//
//                                //add to 1D array
//                                dataArray = new int[data.size()];
//                                entityDataArray = new int[entityData.size()];
//
//                                for (int j = 0; j < dataArray.length; j++) {
//                                    //fill the 1D int array
//                                    dataArray[j] = data.get(j).getAsInt();
//
//                                }
//
//                                for (int j = 0; j < entityDataArray.length; j++) {
//                                    //fill the 1D int array
//                                    entityDataArray[j] = entityData.get(j).getAsInt();
//
//                                }
//
//                            }
//                        }
//                    }
//                }
//
//                EntityData finalEntityData = new EntityData(width, height, collideList, image, health, damage, maxHealth, speed,
//                                          attackSpeed, attackForce, dashSpeed, dashDuration);
//
//
//
//                template.put(entityList.toUpperCase(), finalEntityData);
//                template.put(type.toUpperCase(), finalEntityData);
//            }
//
//            catch (RuntimeException e) {
//                System.err.println("failed to load: " + entityList);
//                e.printStackTrace();
//            }
//        }




    }


    //getters and setters, do this after the serialization works
    public EntityData getEntityData(DataKey dataType) {
        return ENTITY_DATA_HASH_MAP.get(dataType);
    }

    public CollisionData getCollisionData(DataKey dataType) {
        return COLLISION_DATA_HASH_MAP.get(dataType);
    }

    public AnimationData getAnimationData(String dataType) {
        return ANIMATION_DATA_HASH_MAP.get(dataType);
    }

    public RoomData getRoomData(String dataType) {
        return ROOM_DATA_HASH_MAP.get(dataType);
    }
}
