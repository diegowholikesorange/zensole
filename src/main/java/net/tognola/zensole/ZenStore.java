package net.tognola.zensole;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ZenStore {

    private final Logger log = LoggerFactory.getLogger(ZenStore.class.getCanonicalName());



    public List<JsonObject> search(String entityName, String fieldName, String fieldValue) throws IOException {

        log.debug("Searching for {}.{}={}", entityName, fieldName, fieldValue);
        validateSearchInput(entityName, fieldName, fieldValue);

        List<JsonObject> result = new ArrayList<>();
        Gson gson = new Gson();

        try (JsonReader reader = new JsonReader(openJsonReader(entityName))) {
            reader.beginArray();
            while (reader.hasNext()) {
                JsonObject nextEntity = gson.fromJson(reader, JsonObject.class);
                if (fieldValue.equalsIgnoreCase(nextEntity.get(fieldName).getAsString())) {
                    result.add(nextEntity);
                }
            }
            reader.endArray();
        }

        log.debug("Searched for {}.{}={}, result: {}", entityName, "_id", fieldValue, result.isEmpty() ? "No match" : result);
        return result;
    }



    private InputStreamReader openJsonReader(String entityName) throws UnsupportedEncodingException {
        InputStream jsonData = getClass().getResourceAsStream("/" + entityName + ".json");
        if (jsonData == null) {
            throw new IllegalStateException("Could not load " + entityName + ".json from classpath. Is this file bundled with the jar ?");
        }
        return new InputStreamReader(jsonData, "UTF-8");
    }



    private void validateSearchInput(String entityName, String fieldName, String fieldValue) {
        if (entityName == null || entityName.isEmpty()) {
            throw new IllegalArgumentException("Entity name must not be null or empty");
        }
        if (fieldName == null || fieldName.isEmpty()) {
            throw new IllegalArgumentException("Field name must not be null or empty");
        }
        if (fieldValue == null || fieldValue.isEmpty()) {
            throw new IllegalArgumentException("Field value must not be null or empty");
        }
    }



    public String[] listFieldsOfEntity(String entityName) throws IOException {
        log.debug("Listing fields for entity {}", entityName);
        validateListFieldsInput(entityName);

        Gson gson = new Gson();
        JsonObject firstEntity = null;
        try (JsonReader reader = new JsonReader(openJsonReader(entityName))) {
            reader.beginArray();
            if (reader.hasNext()) {
                firstEntity = gson.fromJson(reader, JsonObject.class);
            }
        }
        if (firstEntity == null) {
            throw new IllegalStateException("No entity found in " + entityName + ".json - is this file empty ?");
        }

        return firstEntity.keySet().stream().toArray(String[]::new);
    }



    private void validateListFieldsInput(String entityName) {
        if (entityName == null || entityName.isEmpty()) {
            throw new IllegalArgumentException("Entity name must not be null or empty");
        }
    }
}
