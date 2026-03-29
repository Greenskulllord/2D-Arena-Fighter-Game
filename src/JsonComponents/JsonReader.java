package JsonComponents;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import com.google.gson.*;

/**
     * a class made to read JSON files. it's implemented to be able to read from any JSON format
     *
     * @author Gavin
     * @version 1.0.0
     * @since 2-8-2026
     */
    public class JsonReader {

        //prevent outside code from breaking method
        private final JsonElement element;

        public JsonReader(JsonElement element)  {

            this.element = element;
        }

     /*
    =====================
    Chainable methods
    =====================
     */

        /**
         * After reading a JSON, can be chained to get a field/member name.
         * Example use: start of chain <----- .getName(name) ----> continue of chain </----->
         * @param name the name of a field array or JSON object
         *
         * @return JSON Object
         */
        public JsonReader getName(String name) {
            if (element.isJsonObject()) {
                JsonObject object = element.getAsJsonObject();
                //return the field name
                return new JsonReader(object.get(name));
            }

            return null;
        }

        /**
         * After reading a JSON, can be chained to get a JSON object inside of a JSON array.
         * Example use: the chain <---- .getIndex(0) ------> continue of chain </---->
         * @param index the index in a JSON field array
         *
         * @return JSON Array
         */
        public JsonReader getIndex(Integer index) {
            if (element.isJsonArray()) {
                JsonArray array = element.getAsJsonArray();

                //return field name
                JsonReader attempt;
                try {
                    attempt = new JsonReader(array.get(index));
                } catch (Exception e) {
                    return null;
                }
                return attempt;
            }

            return null;
        }

        /**
         * a helper method that calls reader in order to read a file.
         * Example use: JSONReader root = JSONReader.read(Your File);
         *
         * @param fileName the file to look at
         * @throws FileNotFoundException no file found
         * @return JSON file object
         */
        public static JsonReader read(String fileName) throws FileNotFoundException {

            return new JsonReader(reader(fileName));
        }

    public static JsonReader arrayread(String fileName) throws FileNotFoundException {

        return new JsonReader(arrayReader(fileName));
    }

        /**
         * After reading a JSON, can be chained to get a JSON object inside of a JSON array or Object.
         * Example use: the chain <---- .asString();
         *
         * @return JSON Primitive - String
         */
        public String asString() {

            if (element == null || element.isJsonNull()) {
                return null;
            }

            return element.getAsString();
        }

        /**
         * After reading a JSON, can be chained to get a JSON object inside of a JSON array or Object.
         * Example use: the chain <---- .asInt();
         *
         * @return JSON Primitive - Integer
         */
        public Integer asInt() {

            if (element == null || element.isJsonNull()) {
                return null;
            }

            return element.getAsInt();
        }

        /**
         * After reading a JSON, can be chained to get a JSON object inside of a JSON array or Object.
         * Example use: the chain <---- .raw();
         *
         * @return JsonElement
         */
        public JsonElement raw() {

            if (element == null || element.isJsonNull()) {
                return null;
            }

            return element;
        }

     /*
    =====================
    getAsInteger methods
    =====================
     */

        /**
         * After reading a JSON, can be chained to get a JSON object inside of a JSON array or Object.
         * Example use: JSONReader read = JSONReader.read(Your File).
         * read.getInteger(value).
         * @param getIntegerValue the Json Primitive value
         * @return Json Primitive - Integer
         */
        public Integer getInteger(String getIntegerValue) {
            JsonObject getFile = element.getAsJsonObject();

            //check if it's a valid int
            if (!getFile.has(getIntegerValue)) { return null; }

            //return the result
            return getFile.get(getIntegerValue).getAsInt();
        }

        /**
         * After reading a JSON, can be chained to get a JSON object inside of a JSON array or Object.
         * Example use: JSONReader read = JSONReader.read(Your File).
         * read.getInteger(name, id, value)
         * @param groupName the Json Object
         * @param ID Index in the array
         * @param getIntegerValue the Json Primitive value
         * @return Json Primitive - Integer
         */
        public Integer getInteger(String groupName, Integer ID, String getIntegerValue) {
            //gets the file
            JsonObject getFile = element.getAsJsonObject();
            //gets the first array field
            JsonArray getArrayField = getFile.getAsJsonArray(groupName);
            //grabs the id of the student
            JsonObject getID = getArrayField.get(ID).getAsJsonObject();

            //check if it's a valid int
            if (!getID.has(getIntegerValue)) { return null; }

            //return the result
            return getID.get(getIntegerValue).getAsInt();
        }

        public Integer getInteger(String groupName, String getIntegerValue) {
            //gets the file
            JsonObject getFile = element.getAsJsonObject();
            //gets the first array field
            JsonObject getObjectField = getFile.getAsJsonObject(groupName);
            //grabs the value in the student
            JsonPrimitive getValue = getObjectField.getAsJsonPrimitive(getIntegerValue);

            //check if it's a valid int
            if (!element.isJsonPrimitive()) { return null; }

            //return the result
            return getValue.getAsInt();
        }

    /*
    =====================
    getAsString methods
    =====================
     */

        /**
         * After reading a JSON, can be chained to get a JSON object inside of a JSON array or Object.
         * Example use: JSONReader read = JSONReader.read(Your File).
         * read.getString(name)
         * @param getStringValue the String you want
         * @return Json Primitive - String
         */
        public String getString(String getStringValue) {
            JsonObject getFile = element.getAsJsonObject();

            //check if it's a valid String
            if (!getFile.has(getStringValue)) { return null; }

            //return the result
            return getFile.get(getStringValue).getAsString();
        }

        /**
         * After reading a JSON, can be chained to get a JSON object inside of a JSON array or Object.
         * Example use: JSONReader read = JSONReader.read(Your File).
         * read.getString(groupName, id, value)
         * @param groupName the Json Object
         * @param ID Index in the array
         * @param getStringValue the Json Primitive value
         * @return Json Primitive - String
         */
        public String getString(String groupName, Integer ID, String getStringValue) {
            //gets the file
            JsonObject getFile = element.getAsJsonObject();
            //gets the first array field
            JsonArray getArrayField = getFile.getAsJsonArray(groupName);
            //grabs the id of the student
            JsonObject getID = getArrayField.get(ID).getAsJsonObject();

            //check if it's a valid int
            if (!getID.has(getStringValue)) { return null; }

            //return the result
            return getID.get(getStringValue).getAsString();
        }

        /**
         * After reading a JSON, can be chained to get a JSON object inside of a JSON array or Object.
         * Example use: JSONReader read = JSONReader.read(Your File).
         * read.getString(groupName, value)
         * @param groupName the Json Object
         * @param getStringValue the Json Primitive value
         * @return Json Primitive - String
         */
        public String getString(String groupName, String getStringValue) {
            //gets the file
            JsonObject getFile = element.getAsJsonObject();
            //gets the first array field
            JsonObject getObjectField = getFile.getAsJsonObject(groupName);
            //grabs the value in the student
            JsonPrimitive getValue = getObjectField.getAsJsonPrimitive(getStringValue);

            //check if it's a valid int
            if (!element.isJsonPrimitive()) { return null; }

            //return the result
            return getValue.getAsString();
        }

    /*
    =====================
    Support methods
    =====================
     */

        //a support class to read file information
        private static JsonObject reader(String filename) throws FileNotFoundException {
            //reads the JSON file
            Reader reader = new BufferedReader(new FileReader(filename));

            //returns JSON Object
            return new Gson().fromJson(reader, JsonObject.class);
        }

    private static JsonArray arrayReader(String filename) throws FileNotFoundException {
        //reads the JSON file
        Reader reader = new BufferedReader(new FileReader(filename));

        //returns JSON Object
        return new Gson().fromJson(reader, JsonArray.class);
    }
    }