package net.tognola.zensole;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class ZenStore {

    private final Logger log = LoggerFactory.getLogger(ZenStore.class.getCanonicalName());



    public List<JsonObject> search(String queryEntityName, String queryFieldName, String queryFieldValue) throws IOException {

        log.debug("Searching for {}.{}={}",
                queryEntityName,
                queryFieldName,
                queryFieldValue);

        validateSearchInput(queryEntityName, queryFieldName, queryFieldValue);

        List<JsonObject> results = searchInStream(
                queryFieldName,
                queryFieldValue,
                openJsonDataStream(queryEntityName));

        log.debug("Searched for {}.{}={}, result: {}",
                queryEntityName,
                "_id",
                queryFieldValue,
                results.isEmpty() ? "No match" : results);

        return results;
    }



    List<JsonObject> searchInStream(String queryFieldName, String queryFieldValue, InputStream jsonStream) throws IOException {

        List<JsonObject> result = new ArrayList<>();
        Gson gson = new Gson();

        try (JsonReader reader = new JsonReader(
                new InputStreamReader(jsonStream, "UTF-8"))) {

            reader.beginArray();
            while (reader.hasNext()) {

                JsonObject nthEntity = gson.fromJson(reader, JsonObject.class);
                String ithFieldValueAsString = convertFieldValueToString(nthEntity.get(queryFieldName));

                if (! queryFieldValue.isEmpty() && ithFieldValueAsString.contains(queryFieldValue)) {
                    result.add(nthEntity);
                }

                if (queryFieldValue.isEmpty() && ithFieldValueAsString.isEmpty()) {
                    result.add(nthEntity);
                }
            }
            reader.endArray();
        }

        return result;
    }



    String convertFieldValueToString(JsonElement ithFieldValue) {
        String ithFieldValueAsString = "";
        if (ithFieldValue != null && ithFieldValue.isJsonPrimitive()) {
            ithFieldValueAsString = ithFieldValue.getAsString();
        }
        if (ithFieldValue != null && ithFieldValue.isJsonArray()) {
            ithFieldValueAsString = ithFieldValue.toString()
                    .replace("[", "")
                    .replace("]", "")
                    .replace("\"", "");
        }
        return ithFieldValueAsString;
    }



    InputStream openJsonDataStream(String entityName) {
        if (entityName == null || entityName.isEmpty()) {
            return null;
        }
        InputStream jsonData = getClass().getResourceAsStream("/" + entityName + ".json");
        if (jsonData == null) {
            throw new IllegalStateException("Could not load " + entityName + ".json from classpath. Is this file bundled with the jar ?");
        }
        return jsonData;
    }



    private void validateSearchInput(String entityName, String fieldName, String fieldValue) {
        if (entityName == null || entityName.isEmpty()) {
            throw new IllegalArgumentException("Entity name must not be null or empty");
        }
        if (fieldName == null || fieldName.isEmpty()) {
            throw new IllegalArgumentException("Field name must not be null or empty");
        }
        if (fieldValue == null) {
            throw new IllegalArgumentException("Field value must not be null");
        }
    }



    public String[] listFieldsOfEntity(String entityName) throws IOException {
        log.debug("Listing fields for entity {}", entityName);
        validateListFieldsInput(entityName);

        Gson gson = new Gson();
        JsonObject firstEntity = null;
        try (JsonReader reader = new JsonReader(new InputStreamReader(openJsonDataStream(entityName), "UTF-8"))) {
            reader.beginArray();
            if (reader.hasNext()) {
                firstEntity = gson.fromJson(reader, JsonObject.class);
            }
        }
        if (firstEntity == null) {
            throw new IllegalStateException("No entity found in " + entityName + ".json - is this file empty ?");
        }

        return firstEntity.keySet().toArray(new String[0]);
    }



    private void validateListFieldsInput(String entityName) {
        if (entityName == null || entityName.isEmpty()) {
            throw new IllegalArgumentException("Entity name must not be null or empty");
        }
    }


}
