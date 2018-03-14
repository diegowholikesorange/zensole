package net.tognola.zensole;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResultRenderer {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";



    public String render(JsonObject testDataObject) {
        List<String> lines = new ArrayList<>();
        for (String key : testDataObject.keySet()) {
            String value = testDataObject.get(key).toString().replace("\"", "");
            String line = String.format("%-20s = %s %s %s", key, ANSI_YELLOW, value, ANSI_RESET);
            lines.add(line);
        }
        Collections.sort(lines);
        return String.join("\n", lines);
    }
}
