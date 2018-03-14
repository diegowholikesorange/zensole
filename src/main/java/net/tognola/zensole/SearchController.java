package net.tognola.zensole;

import com.google.gson.JsonObject;

import java.io.IOException;

public class SearchController {

    private final ZenStore zenStore;



    public SearchController(ZenStore zenStore) {
        this.zenStore = zenStore;
    }



    public JsonObject search(String entityName, String fieldValue) throws IOException {
        return zenStore.search(entityName, fieldValue);
    }



    public String[] listFieldsOfEntity(String entityName) {
        return zenStore.listFieldsOfEntity(entityName);
    }
}
