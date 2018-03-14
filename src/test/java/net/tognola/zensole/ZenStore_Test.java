package net.tognola.zensole;

import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import static org.assertj.core.api.Assertions.*;

public class ZenStore_Test {

    private ZenStore zenStore;



    @Before
    public void setup() {
        zenStore = new ZenStore();
    }



    @Test
    public void searchShouldValidateInput() {
        assertThatThrownBy(() -> zenStore.search(null, "123"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Entity name must not be null");

        assertThatThrownBy(() -> zenStore.search("tickets", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Field value must not be null");
    }



    @Test
    public void listFieldsOfEntityShouldNotBeEmpty() {
        assertThat(zenStore.listFieldsOfEntity(null)).isNotEmpty();
    }



    @Test
    public void listFieldsOfEntityForTicketsShouldContainId() {
        assertThat(zenStore.listFieldsOfEntity("tickets")).contains("_id");
    }



    @Test
    public void searchByTicket_shouldReturnCorrectTicket_whenTicketExists() throws IOException {
        JsonObject results = zenStore.search("tickets", "6aac0369-a7e5-4417-8b50-92528ef485d3");
        assertThat(results.has("_id")).isTrue();
        assertThat(results.get("_id").getAsString()).isEqualTo("6aac0369-a7e5-4417-8b50-92528ef485d3");
        assertThat(results.get("organization_id").getAsInt()).isEqualTo(113);
    }



    @Test
    public void searchByTicket_shouldReturnNull_whenTicketDoesNotExist() throws IOException {
        JsonObject results = zenStore.search("tickets", "555-555");
        assertThat(results).isNull();
    }


}
