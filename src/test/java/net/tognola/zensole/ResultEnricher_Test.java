package net.tognola.zensole;

import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class ResultEnricher_Test {

    private ResultEnricher enricher;
    private ZenStore zenStore;



    @Before
    public void setup() {
        enricher = new ResultEnricher();
        zenStore = new ZenStore();
    }



    @Test
    public void enrichTicketsShouldReturnFullList() throws IOException {
        List<JsonObject> result = zenStore.search("tickets", "organization_id", "112");
        int originalSize = result.size();
        assertThat(enricher.enrich(result, zenStore)).hasSize(originalSize);
    }



    @Test
    public void enrichTicketsShouldReplaceOrganizationIdWithDetail() throws IOException {
        JsonObject result = zenStore.search("test-tickets", "_id", "2217c7dc-7371-4401-8738-0a8a8aedc08d").get(0);
        JsonObject enriched = enricher.enrichItem(result, zenStore);
        assertThat(enriched.get("organization").getAsString()).contains("Xylar (id=104)");
        assertThat(enriched.get("organization")).isNotNull();
        assertThat(enriched.get("organization_id")).isNull();
    }



    @Test
    public void enrichTicketsShouldReplaceSubmitterIdWithDetail() throws IOException {
        JsonObject result = zenStore.search("test-tickets", "_id", "2217c7dc-7371-4401-8738-0a8a8aedc08d").get(0);
        JsonObject enriched = enricher.enrichItem(result, zenStore);
        assertThat(enriched.get("submitter").getAsString()).contains("Josefa Mcfadden (id=9)");
        assertThat(enriched.get("submitter")).isNotNull();
        assertThat(enriched.get("submitter_id")).isNull();
    }



    @Test
    public void enrichTicketsShouldReplaceAssigneeIdWithDetail() throws IOException {
        JsonObject result = zenStore.search("test-tickets", "_id", "2217c7dc-7371-4401-8738-0a8a8aedc08d").get(0);
        JsonObject enriched = enricher.enrichItem(result, zenStore);
        assertThat(enriched.get("assignee").getAsString()).contains("Deanna Terry (id=65)");
        assertThat(enriched.get("assignee")).isNotNull();
        assertThat(enriched.get("assignee_id")).isNull();
    }



    @Test
    public void enrichUsersShouldReturnFullList() throws IOException {
        List<JsonObject> result = zenStore.search("users", "organization_id", "123");
        int originalSize = result.size();
        assertThat(enricher.enrich(result, zenStore)).hasSize(originalSize);
    }



    @Test
    public void enrichUsersShouldReplaceOrganizationIdWithDetail() throws IOException {
        JsonObject result = zenStore.search("users", "_id", "65").get(0);
        JsonObject enriched = enricher.enrichItem(result, zenStore);
        assertThat(enriched.get("organization").getAsString()).contains("Terrasys (id=123)");
        assertThat(enriched.get("organization")).isNotNull();
        assertThat(enriched.get("organization_id")).isNull();
    }

}
