package net.tognola.zensole;

import com.google.gson.JsonObject;

public class ResultRenderer {


    public String render(JsonObject testDataObject) {
        return testDataObject.toString();
    }
}
