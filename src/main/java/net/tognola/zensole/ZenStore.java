package net.tognola.zensole;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ZenStore {

    private final Logger log = LoggerFactory.getLogger(ZenStore.class.getCanonicalName());



    public JsonObject search(String entityName, String fieldValue) throws IOException {

        log.debug("Searching for {}.{}={}", entityName, "_id", fieldValue);
        validateSearchInput(entityName, fieldValue);

        JsonObject result = null;
        Gson gson = new Gson();

        InputStream jsonData = openJsonDataStream(entityName);
        JsonReader reader = new JsonReader(new InputStreamReader(jsonData, "UTF-8"));
        reader.beginArray();
        while (reader.hasNext()) {
            JsonObject message = gson.fromJson(reader, JsonObject.class);
            if (fieldValue.equalsIgnoreCase(message.get("_id").getAsString())) {
                result = message;
                break;
            }
        }
        if (result == null) {
            reader.endArray();
        }
        reader.close();

        log.debug("Searched for {}.{}={}, result: {}", entityName, "_id", fieldValue, result == null ? "No match" : result);
        return result;
    }



    private InputStream openJsonDataStream(String entityName) {
        InputStream jsonData = getClass().getResourceAsStream("/" + entityName + ".json");
        if (jsonData == null) {
            throw new IllegalStateException("Could not load " + entityName + ".json from classpath");
        }
        return jsonData;
    }



    private void validateSearchInput(String entityName, String fieldValue) {
        if (entityName == null) {
            throw new IllegalArgumentException("Entity name must not be null");
        }
        if (fieldValue == null) {
            throw new IllegalArgumentException("Field value must not be null");
        }
    }



    public String[] listFieldsOfEntity(String entityName) {
        log.debug("Listing fields for entity {}", entityName);
        return new String[]{"_id", "status"};
    }
}
