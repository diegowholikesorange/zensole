package net.tognola.zensole;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Run a search against a set of 100,000 records.
 * The only check we do here however is that the search does not blow up.
 */
public class ZenStore_PerformanceTest {

    private ZenStore zenStore;
    private InputStream largeUserJsonDataStream;



    @Before
    public void setUp() throws Exception {
        zenStore = new ZenStore();
        JsonObject template = zenStore.search("users", "role", "admin").get(0);

        JsonArray largeUserStore = new JsonArray();
        for (int i = 0; i < 100000; i++) {
            largeUserStore.add(template);
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String newJsonString = gson.toJson(largeUserStore);
        largeUserJsonDataStream = new ByteArrayInputStream(newJsonString.getBytes());
    }



    @Test
    public void runSearch() throws IOException {
        zenStore.searchInStream("role", "", largeUserJsonDataStream);
    }
}
