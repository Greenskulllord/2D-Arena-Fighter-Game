package JsonComponents;
import com.google.gson.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class JsonGenerator {

    //the builder is what gets the formatting
    Gson gson = new GsonBuilder()
            .setFormattingStyle(FormattingStyle.PRETTY) //makes good formatting
            .create(); //creates GSON instance


    public JsonGenerator(String filename) {

        //get the JSON writer going
        try (BufferedWriter bWriter = new BufferedWriter(new FileWriter(filename))) {

            String line = gson.toJson(data());

            //makes it write in the data it has
            bWriter.write(line);
            bWriter.newLine();


        } catch (IOException e) {
            System.out.print("issue saving file");
            e.printStackTrace();
        }
    }

    /*
    =================================
    Pre-determined info
    =================================
    */

    private Object data () {
        //call the classes
        JsonArray statsList = new JsonArray();
        JsonObject template = new JsonObject();
        JsonObject stats = new JsonObject();
        JsonArray collideList = new JsonArray();

        //add stuff to array
        collideList.add("PLAYER");
        collideList.add("WALL");

        //add properties to stats
        stats.addProperty("width", 0);
        stats.addProperty("height", 0);
        stats.addProperty("health", 0);
        stats.addProperty("collisionLayer", "layer");
        stats.add("canCollideWith", collideList);

        //add stats to stats list
        statsList.add(stats);

        //add stats to entity list
        template.add("Entity_Name", statsList);

        //return the final list
        return template;
    }

}
