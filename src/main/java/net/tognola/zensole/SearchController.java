package net.tognola.zensole;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;

class SearchController {

    private final ZenStore zenStore;



    public SearchController(ZenStore zenStore) {
        this.zenStore = zenStore;
    }



    public List<JsonObject> search(String entityName, String fieldName, String fieldValue) throws IOException {
        return zenStore.search(entityName, fieldName, fieldValue);
    }



    public String[] listFieldsOfEntity(String entityName) throws IOException {
        return zenStore.listFieldsOfEntity(entityName);
    }
}
