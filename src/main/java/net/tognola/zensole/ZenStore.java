package net.tognola.zensole;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZenStore {

    private final Logger log = LoggerFactory.getLogger(ZenStore.class.getCanonicalName());



    public JsonObject search() {
        log.debug("Searching {} for {}={}", "tickets", "_id", "123");
        String json = "{\n" +
                "    \"_id\": \"7382ad0e-dea7-4c8d-b38f-cbbf016f2598\",\n" +
                "    \"url\": \"http://initech.zendesk.com/api/v2/tickets/7382ad0e-dea7-4c8d-b38f-cbbf016f2598.json\",\n" +
                "    \"external_id\": \"6d3b0e05-6013-4513-9913-0bb6a0f66ef7\",\n" +
                "    \"created_at\": \"2016-03-31T03:16:52 -11:00\",\n" +
                "    \"type\": \"task\",\n" +
                "    \"subject\": \"A Problem in American Samoa\",\n" +
                "    \"description\": \"Excepteur dolor in commodo minim irure laboris. In incididunt mollit veniam pariatur ullamco laborum ullamco aliqua do fugiat Lorem.\",\n" +
                "    \"priority\": \"high\",\n" +
                "    \"status\": \"closed\",\n" +
                "    \"submitter_id\": 35,\n" +
                "    \"assignee_id\": 64,\n" +
                "    \"organization_id\": 118,\n" +
                "    \"tags\": [\n" +
                "      \"Missouri\",\n" +
                "      \"Alabama\",\n" +
                "      \"Virginia\",\n" +
                "      \"Virgin Islands\"\n" +
                "    ],\n" +
                "    \"has_incidents\": true,\n" +
                "    \"due_at\": \"2016-08-06T08:36:17 -10:00\",\n" +
                "    \"via\": \"chat\"\n" +
                "  }";
        return (new JsonParser()).parse(json).getAsJsonObject();
    }



    public String[] listFieldsOfEntity() {
        String entityType = "(unknown)";
        log.debug("Listing fields for entity type {}", entityType);
        return new String[]{"_id", "status"};
    }
}
