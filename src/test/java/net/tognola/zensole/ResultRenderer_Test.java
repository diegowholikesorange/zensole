package net.tognola.zensole;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class ResultRenderer_Test {

    private ResultRenderer renderer;


    private List<JsonObject> fakeResult;



    @Before
    public void setup() {
        final String json = "{\n" +
                "    \"_id\": \"7382ad0e-dea7-4c8d-b38f-cbbf016f2598\",\n" +
                "    \"url\": \"http://initech.zendesk.com/api/v2/tickets/7382ad0e-dea7-4c8d-b38f-cbbf016f2598.json\",\n" +
                "    \"external_id\": \"6d3b0e05-6013-4513-9913-0bb6a0f66ef7\",\n" +
                "    \"created_at\": \"2016-03-31T03:16:52 -11:00\",\n" +
                "    \"type\": \"task\",\n" +
                "    \"subject\": \"A Problem in American Samoa\",\n" +
                "    \"description\": \"Excepteur dolor in commodo minim irure laboris.\",\n" +
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
        fakeResult = new ArrayList<>();
        fakeResult.add(new JsonParser().parse(json).getAsJsonObject());
        renderer = new ResultRenderer();
    }



    @Test
    public void shouldRenderEachAttributeOnSeparateLine() {
        final String rendered = renderer.render(fakeResult);
        System.out.println(rendered);
        final String[] lines = rendered.split("\n");
        assertThat(lines.length).isEqualTo(19);
    }



    @Test
    public void shouldRenderAllKeysAndValues() {
        final String rendered = renderer.render(fakeResult);
        assertThat(rendered).isNotEmpty();
        for (final String key : fakeResult.get(0).keySet()) {
            assertThat(rendered).contains(key);
            final String valueAsStringWithoutQuotes = fakeResult.get(0).get(key).toString().replace("\"", "");
            assertThat(rendered).contains(valueAsStringWithoutQuotes);
        }
    }



    @Test
    public void shouldRenderEmptyResult() {
        fakeResult = new ArrayList<>();
        final String rendered = renderer.render(fakeResult);
        assertThat(rendered).isNotEmpty();
        assertThat(rendered).contains("No matches found");
    }
}
