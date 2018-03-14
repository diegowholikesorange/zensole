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
        assertThat(zenStore.listFieldsOfEntity("users")).isNotEmpty();
    }



    @Test
    public void listFieldsOfEntityForUsersShouldContainAllFields() throws IOException {
        String[] fields = zenStore.listFieldsOfEntity("users");
        assertThat(fields).contains("_id");
        assertThat(fields).contains("url");
        assertThat(fields).contains("external_id");
        assertThat(fields).contains("name");
        assertThat(fields).contains("alias");
        assertThat(fields).contains("created_at");
        assertThat(fields).contains("active");
        assertThat(fields).contains("verified");
        assertThat(fields).contains("shared");
        assertThat(fields).contains("locale");
        assertThat(fields).contains("timezone");
        assertThat(fields).contains("last_login_at");
        assertThat(fields).contains("email");
        assertThat(fields).contains("phone");
        assertThat(fields).contains("signature");
        assertThat(fields).contains("organization_id");
        assertThat(fields).contains("tags");
        assertThat(fields).contains("suspended");
        assertThat(fields).contains("role");
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
        JsonObject result = zenStore.search("tickets", "_id", "6aac0369-a7e5-4417-8b50-92528ef485d3").get(0);
        assertThat(result.has("_id")).isTrue();
        assertThat(result.get("_id").getAsString()).isEqualTo("6aac0369-a7e5-4417-8b50-92528ef485d3");
        assertThat(result.get("organization_id").getAsInt()).isEqualTo(113);
    }



    @Test
    public void searchByTicketId_shouldReturnEmpty_whenTicketDoesNotExist() throws IOException {
        List<JsonObject> result = zenStore.search("tickets", "_id", "555-555");
        assertThat(result).isEmpty();
    }



    @Test
    public void searchForOptionalFieldShouldSucceed() throws IOException {
        List<JsonObject> result = zenStore.search("tickets", "organization_id", "112");
        assertThat(result).hasSize(5);
    }



    @Test
    public void searchTicketByOrganizationId_shouldReturnCorrectTickets() throws IOException {
        List<JsonObject> result = zenStore.search("tickets", "organization_id", "112");
        String renderedContent = new ResultRenderer().render(result);
        assertThat(renderedContent).contains("1a227508-9f39-427c-8f57-1b72f3fab87c");
        assertThat(renderedContent).contains("5507c3f7-27fe-48f1-b01e-46d31715cc62");
        assertThat(renderedContent).contains("cb3b726e-9ba0-4e35-b4d6-ee41c29a7185");
        assertThat(renderedContent).contains("4d22436c-6c26-431b-9083-35ec8e86c57d");
        assertThat(renderedContent).contains("0533df4e-488f-45dd-b4b8-e238be0690ed");
    }



    @Test
    public void searchUserByEmail_shouldReturnCorrectUser() throws IOException {
        List<JsonObject> result = zenStore.search("users", "email", "coffeyrasmussen@flotonic.com");
        assertThat(result).hasSize(1);
        String renderedContent = new ResultRenderer().render(result);
        assertThat(renderedContent).contains("Francisca Rasmussen");
    }



    @Test
    public void searchUserByTags_shouldReturnCorrectUser() throws IOException {
        List<JsonObject> result = zenStore.search("users", "tags", "Bonanza");
        assertThat(result).hasSize(1);
        String renderedContent = new ResultRenderer().render(result);
        assertThat(renderedContent).contains("38899b1e-89ca-43e7-b039-e3c88525f0d2");
    }

}
