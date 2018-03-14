package net.tognola.zensole;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ZenSoleCli_JourneyTest {

    private ZenSoleCli cli;



    @Test
    public void searchByTicketIdAndExitShouldReturnResult() {
        InputStream simulatedConsoleInput = new ByteArrayInputStream("1\n 0\n 6aac0369-a7e5-4417-8b50-92528ef485d3\n".getBytes());
        cli = new ZenSoleCli(simulatedConsoleInput);

        String searchResults = cli.collectSearchCriteriaAndRunSearch();
        System.out.println("\n" + searchResults);

//        Assertions.assertThat(searchResults).contains("6aac0369-a7e5-4417-8b50-92528ef485d3");
    }


}
