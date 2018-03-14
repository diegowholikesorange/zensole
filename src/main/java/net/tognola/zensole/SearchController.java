package net.tognola.zensole;

import com.google.gson.JsonObject;

public class SearchController {

    private final ZenStore zenStore;



    public SearchController(ZenStore zenStore) {
        this.zenStore = zenStore;
    }



    public JsonObject search() {
        return zenStore.search();
    }



    public String[] listFieldsOfEntity(String entityName) {
        return zenStore.listFieldsOfEntity(entityName);
    }
}
