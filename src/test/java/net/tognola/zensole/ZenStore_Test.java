package net.tognola.zensole;

import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

public class ZenStore_Test {

    private ZenStore zenStore;



    @Before
    public void setup() {
        zenStore = new ZenStore();
    }



    @Test
    public void searchShouldValidateResource() {
        assertThatThrownBy(() -> zenStore.search("eggs", "id", "123"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Could not load eggs.json from classpath. Is this file bundled with the jar ?");
    }



    @Test
    public void searchShouldValidateInput() {
        assertThatThrownBy(() -> zenStore.search(null, "_id", "123"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Entity name must not be null or empty");
        assertThatThrownBy(() -> zenStore.search("", "_id", "123"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Entity name must not be null or empty");

        assertThatThrownBy(() -> zenStore.search("tickets", null, "123"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Field name must not be null or empty");
        assertThatThrownBy(() -> zenStore.search("tickets", "", "123"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Field name must not be null or empty");

        assertThatThrownBy(() -> zenStore.search("tickets", "_id", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Field value must not be null or empty");
        assertThatThrownBy(() -> zenStore.search("tickets", "_id", ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Field value must not be null or empty");
    }



    @Test
    public void listFieldsOfEntityShouldValidateInput() {
        assertThatThrownBy(() -> zenStore.listFieldsOfEntity(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Entity name must not be null or empty");
        assertThatThrownBy(() -> zenStore.listFieldsOfEntity(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Entity name must not be null or empty");
    }



    @Test
    public void listFieldsOfEntityShouldNotBeEmpty() throws IOException {
        assertThat(zenStore.listFieldsOfEntity("tickets")).isNotEmpty();
    }



    @Test
    public void listFieldsOfEntityForTicketsShouldContainAllFields() throws IOException {
        String[] fields = zenStore.listFieldsOfEntity("tickets");
        assertThat(fields).contains("_id");
        assertThat(fields).contains("url");
        assertThat(fields).contains("external_id");
        assertThat(fields).contains("created_at");
        assertThat(fields).contains("type");
        assertThat(fields).contains("subject");
        assertThat(fields).contains("description");
        assertThat(fields).contains("priority");
        assertThat(fields).contains("status");
        assertThat(fields).contains("submitter_id");
        assertThat(fields).contains("assignee_id");
        assertThat(fields).contains("organization_id");
        assertThat(fields).contains("tags");
        assertThat(fields).contains("has_incidents");
        assertThat(fields).contains("due_at");
        assertThat(fields).contains("via");
    }



    @Test
    public void searchByTicketId_shouldReturnCorrectTicket_whenTicketExists() throws IOException {
        JsonObject results = zenStore.search("tickets", "_id", "6aac0369-a7e5-4417-8b50-92528ef485d3").get(0);
        assertThat(results.has("_id")).isTrue();
        assertThat(results.get("_id").getAsString()).isEqualTo("6aac0369-a7e5-4417-8b50-92528ef485d3");
        assertThat(results.get("organization_id").getAsInt()).isEqualTo(113);
    }



    @Test
    public void searchByTicketId_shouldReturnEmpty_whenTicketDoesNotExist() throws IOException {
        List<JsonObject> results = zenStore.search("tickets", "_id", "555-555");
        assertThat(results).isEmpty();
    }


}
