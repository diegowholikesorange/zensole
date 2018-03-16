package net.tognola.zensole.gui;

import org.junit.Test;

import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;

public class ZenSoleCli_JourneyTest {

    private ZenSoleCli cli;



    @Test
    public void findAllUsersForOrganization() throws IOException {
        cli = new ZenSoleCli("2", "15", "119");

        String searchResults = cli.collectSearchCriteriaAndReturnSearchResult();

        assertThat(searchResults).contains("Francisca Rasmussen");
        assertThat(searchResults).contains("Multron (id=119)");
        assertThat(searchResults).contains("Don't Worry Be Happy!");
        assertThat(searchResults).contains("Pitts Park");
        assertThat(searchResults).contains("9974-742-963");
        assertThat(searchResults).contains("Moran Daniels");
        assertThat(searchResults).contains("Tokelau");
        assertThat(searchResults).contains("Catalina Simpson");
        assertThat(searchResults).contains("rosannasimpson@flotonic.com");
    }



    @Test
    public void findAllTicketsForOrganization() throws IOException {
        cli = new ZenSoleCli("1", "11", "121");

        String searchResults = cli.collectSearchCriteriaAndReturnSearchResult();

        assertThat(searchResults).contains("6f2eca87-8425-40f5-b12c-6745039d12f6");
        assertThat(searchResults).contains("ca106ab2-84af-45e7-a101-2d5c63eebf85");
        assertThat(searchResults).contains("9686f505-6bf0-4972-9ba3-9b5c2fe8f725");
        assertThat(searchResults).contains("A Drama in United Arab Emirates");
        assertThat(searchResults).contains("Jaime Dickerson (id=33)");
        assertThat(searchResults).contains("Francis Rodrigüez (id=19)");
        assertThat(searchResults).contains("tickets/c527e065-ec62-40ed-aa72-136f5ab0eb89.json");
        assertThat(searchResults).contains("Consequat sit sint velit anim laboris adipisicing");
        assertThat(searchResults).contains("West Virginia");
        assertThat(searchResults).contains("Fédératéd Statés Of Micronésia");
        assertThat(searchResults).contains("A Drama in United Arab Emirates");
    }



    @Test
    public void findAllTicketsWithNoOrganization() throws IOException {
        cli = new ZenSoleCli("1", "11", "");

        String searchResults = cli.collectSearchCriteriaAndReturnSearchResult();

        assertThat(searchResults).contains("total 4 matches");
        assertThat(searchResults).contains("e68d8bfd-9826-42fd-9692-add445aa7430");
        assertThat(searchResults).contains("4b88dee7-0c17-4fe2-8cb6-914b7ce93dc3");
        assertThat(searchResults).contains("54f60187-6064-492a-9a4c-37fc21b4e300");
        assertThat(searchResults).contains("f2379173-6083-49f9-a001-8310f6478b4e");
    }



    @Test
    public void showAllTicketDetailsWhenTicketExists() throws IOException {
        cli = new ZenSoleCli("1", "0", "6aac0369-a7e5-4417-8b50-92528ef485d3");

        String searchResults = cli.collectSearchCriteriaAndReturnSearchResult();

        assertThat(searchResults).contains("6aac0369-a7e5-4417-8b50-92528ef485d3");
        assertThat(searchResults).contains("Herrera Norman (id=29)");
        assertThat(searchResults).contains("2016-06-15T12:03:55 -10:00");
        assertThat(searchResults).contains("Laboris laborum culpa sit culpa minim ad laborum Lorem laboris aliqua tempor. ");
        assertThat(searchResults).contains("2016-08-16T05:52:08 -10:00");
        assertThat(searchResults).contains("0c2ba6c6-ea9a-4a58-ada4-bc72f3b9ff39");
        assertThat(searchResults).contains("false");
        assertThat(searchResults).contains("Noralex (id=113)");
        assertThat(searchResults).contains("urgent");
        assertThat(searchResults).contains("hold");
        assertThat(searchResults).contains("A Nuisance in Latvia");
        assertThat(searchResults).contains("Daniel Agüilar (id=50)");
        assertThat(searchResults).contains("Washington,Wyoming,Ohio,Pennsylvania");
        assertThat(searchResults).contains("question");
        assertThat(searchResults).contains("http://initech.zendesk.com/api/v2/tickets/6aac0369-a7e5-4417-8b50-92528ef485d3.json");
        assertThat(searchResults).contains("chat");
    }



    @Test
    public void showNoticeWhenNoTicketExists() throws IOException {
        cli = new ZenSoleCli("1", "0", "555-555");

        String searchResults = cli.collectSearchCriteriaAndReturnSearchResult();

        assertThat(searchResults).contains("No matches found");
    }
}
