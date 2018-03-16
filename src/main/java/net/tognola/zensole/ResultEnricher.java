package net.tognola.zensole;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

class ResultEnricher {

    private final Logger log = LoggerFactory.getLogger(ResultEnricher.class.getCanonicalName());



    public List<JsonObject> enrich(List<JsonObject> searchResults, ZenStore store) throws IOException {

        for (JsonObject resultItem : searchResults) {
            enrichItem(resultItem, store);
        }
        return searchResults;
    }



    JsonObject enrichItem(JsonObject nthResult, ZenStore store) throws IOException {
        replaceIdWithDetails(nthResult, "organization_id", "organizations", "organization", store);
        replaceIdWithDetails(nthResult, "submitter_id", "users", "submitter", store);
        replaceIdWithDetails(nthResult, "assignee_id", "users", "assignee", store);
        return nthResult;
    }



    private void replaceIdWithDetails(JsonObject resultItem, String idFieldName, String referenceEntityName, String detailsFieldName, ZenStore store) throws IOException {
        if (resultItem.get(idFieldName) != null) {
            String idFieldValue = resultItem.get(idFieldName).getAsString();
            List<JsonObject> references = store.search(referenceEntityName, "_id", idFieldValue);
            if (references != null && ! references.isEmpty()) {
                JsonObject reference = references.get(0);
                String replacementText = String.format("%s (id=%s)", reference.get("name").getAsString(), idFieldValue);
                resultItem.add(detailsFieldName, new JsonPrimitive(replacementText));
                resultItem.remove(idFieldName);
            }
            else {
                log.warn("Unable to enrich result with references because references with {}={} could not be found. (Data integrity issue ?)", idFieldName, idFieldValue);
            }
        }
    }
}
