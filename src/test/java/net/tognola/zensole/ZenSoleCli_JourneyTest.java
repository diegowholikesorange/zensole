package net.tognola.zensole;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.IOException;

public class ZenSoleCli_JourneyTest {

    private ZenSoleCli cli;



    @Test
    public void findAllUsersForOrganization() throws IOException {
        cli = new ZenSoleCli("2", "15", "119", "0");
        String searchResults = cli.collectSearchCriteriaAndReturnSearchResult();
        Assertions.assertThat(searchResults).contains("Francisca Rasmussen");
        Assertions.assertThat(searchResults).contains("Don't Worry Be Happy!");
        Assertions.assertThat(searchResults).contains("Pitts Park");
        Assertions.assertThat(searchResults).contains("9974-742-963");
        Assertions.assertThat(searchResults).contains("Moran Daniels");
        Assertions.assertThat(searchResults).contains("Tokelau");
        Assertions.assertThat(searchResults).contains("Catalina Simpson");
        Assertions.assertThat(searchResults).contains("rosannasimpson@flotonic.com");
        cli.collectSearchCriteriaAndReturnSearchResult();
    }



    @Test
    public void findAllTicketsForOrganization() throws IOException {
        cli = new ZenSoleCli("1", "11", "121", "0");
        String searchResults = cli.collectSearchCriteriaAndReturnSearchResult();
        Assertions.assertThat(searchResults).contains("6f2eca87-8425-40f5-b12c-6745039d12f6");
        Assertions.assertThat(searchResults).contains("ca106ab2-84af-45e7-a101-2d5c63eebf85");
        Assertions.assertThat(searchResults).contains("9686f505-6bf0-4972-9ba3-9b5c2fe8f725");
        Assertions.assertThat(searchResults).contains("A Drama in United Arab Emirates");
        Assertions.assertThat(searchResults).contains("tickets/c527e065-ec62-40ed-aa72-136f5ab0eb89.json");
        Assertions.assertThat(searchResults).contains("Consequat sit sint velit anim laboris adipisicing");
        Assertions.assertThat(searchResults).contains("West Virginia");
        Assertions.assertThat(searchResults).contains("Fédératéd Statés Of Micronésia");
        Assertions.assertThat(searchResults).contains("A Drama in United Arab Emirates");
        cli.collectSearchCriteriaAndReturnSearchResult();
    }



    @Test
    public void showAllTicketDetailsWhenTicketExists() throws IOException {
        cli = new ZenSoleCli("1", "0", "6aac0369-a7e5-4417-8b50-92528ef485d3", "0");
        String searchResults = cli.collectSearchCriteriaAndReturnSearchResult();

        Assertions.assertThat(searchResults).contains("6aac0369-a7e5-4417-8b50-92528ef485d3");
        Assertions.assertThat(searchResults).contains("29");
        Assertions.assertThat(searchResults).contains("2016-06-15T12:03:55 -10:00");
        Assertions.assertThat(searchResults).contains("Laboris laborum culpa sit culpa minim ad laborum Lorem laboris aliqua tempor. ");
        Assertions.assertThat(searchResults).contains("2016-08-16T05:52:08 -10:00");
        Assertions.assertThat(searchResults).contains("0c2ba6c6-ea9a-4a58-ada4-bc72f3b9ff39");
        Assertions.assertThat(searchResults).contains("false");
        Assertions.assertThat(searchResults).contains("113");
        Assertions.assertThat(searchResults).contains("urgent");
        Assertions.assertThat(searchResults).contains("hold");
        Assertions.assertThat(searchResults).contains("A Nuisance in Latvia");
        Assertions.assertThat(searchResults).contains("50");
        Assertions.assertThat(searchResults).contains("Washington,Wyoming,Ohio,Pennsylvania");
        Assertions.assertThat(searchResults).contains("question");
        Assertions.assertThat(searchResults).contains("http://initech.zendesk.com/api/v2/tickets/6aac0369-a7e5-4417-8b50-92528ef485d3.json");
        Assertions.assertThat(searchResults).contains("chat");
        cli.collectSearchCriteriaAndReturnSearchResult();
    }



    @Test
    public void showNoticeWhenNoTicketExists() throws IOException {
        cli = new ZenSoleCli("1", "0", "555-555", "0");

        String searchResults = cli.collectSearchCriteriaAndReturnSearchResult();
        Assertions.assertThat(searchResults).contains("No matches found");
    }
}
