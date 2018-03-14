package net.tognola.zensole;

import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ZenStore_Test {

    private ZenStore zenStore;



    @Before
    public void setup() {
        zenStore = new ZenStore();
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
    public void searchByTicket_shouldReturnCorrectTicket_whenTicketExists() {
        JsonObject results = zenStore.search();
        assertThat(results.has("_id")).isTrue();
        assertThat(results.get("_id").getAsString()).isEqualTo("7382ad0e-dea7-4c8d-b38f-cbbf016f2598");
        assertThat(results.get("organization_id").getAsInt()).isEqualTo(118);
    }


}
