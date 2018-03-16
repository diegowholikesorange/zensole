package net.tognola.zensole;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

class SearchController {

    private final ZenStore zenStore;
    private final ResultEnricher enricher;



    public SearchController(ZenStore zenStore, ResultEnricher enricher) {
        this.zenStore = zenStore;
        this.enricher = enricher;
    }



    public List<JsonObject> search(String entityName, String fieldName, String fieldValue) throws IOException {
        List<JsonObject> searchResults = zenStore.search(entityName, fieldName, fieldValue);
        return enricher.enrich(searchResults, zenStore);
    }



    public String[] listFieldsOfEntity(String entityName) throws IOException {
        return zenStore.listFieldsOfEntity(entityName);
    }
}
