package Json_Components;
import com.google.gson.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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

    private JsonArray data () {
        //number of node templates to generate
        int nodeCount = 50;
        int edgeCount = 5;

        //make the array
        JsonArray nodeList = new JsonArray();

        for (int i = 1; i <= nodeCount; i++) {

            //objects
            JsonObject node = new JsonObject();

            //give nodes properties
            node.addProperty("node", "name " + i);

            //make the edgeList
            JsonArray edgeList = new JsonArray();

            //for loop here
            for (int a = 1; a <= edgeCount; a++) {
                JsonObject edge = new JsonObject();

                //give edges properties
                edge.addProperty("edgename", "name" + a);
                edge.addProperty("weight", 0);
                edge.addProperty("directions", "write here.");
                edgeList.add(edge);

                //add edge list to node
                node.add("edges", edgeList);
            }

            //add node to node list
            nodeList.add(node);
        }

        //send the data out
        return nodeList;
    }

}
