package net.tognola.zensole.gui;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResultRenderer {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    private static final String DELIMITER = String.format("%n%50s%n", "").replace(" ", "-");



    public String render(List<JsonObject> result) {

        if (result == null || result.isEmpty()) {
            return "No matches found.";
        }

        StringBuilder rendered = new StringBuilder();
        rendered.append(String.format(
                result.size() > 1 ? "total %s matches" : "total %s match",
                result.size()));
        rendered.append(DELIMITER);

        for (JsonObject jsonObject : result) {

            List<String> lines = new ArrayList<>();
            for (String key : jsonObject.keySet()) {
                String value = jsonObject.get(key).toString().replace("\"", "");
                String line = String.format("%-20s = %s %s %s",
                        key, ANSI_YELLOW, value, ANSI_RESET);
                lines.add(line);
            }
            
            Collections.sort(lines);
            rendered.append(String.join("\n", lines));
            rendered.append(DELIMITER);
        }
        return rendered.toString();
    }
}
