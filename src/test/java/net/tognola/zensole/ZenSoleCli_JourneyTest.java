package net.tognola.zensole;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ZenSoleCli_JourneyTest {

    private ZenSoleCli cli;



    @Test
    public void showAllTicketDetailsWhenTicketExists() throws IOException {
        InputStream simulatedConsoleInput = new ByteArrayInputStream("1\n 0\n 6aac0369-a7e5-4417-8b50-92528ef485d3\n 0\n".getBytes());
        cli = new ZenSoleCli(simulatedConsoleInput);

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
        InputStream simulatedConsoleInput = new ByteArrayInputStream("1\n 0\n 555-555\n 0\n".getBytes());
        cli = new ZenSoleCli(simulatedConsoleInput);

        String searchResults = cli.collectSearchCriteriaAndReturnSearchResult();
        Assertions.assertThat(searchResults).contains("No matches found");
    }
}
