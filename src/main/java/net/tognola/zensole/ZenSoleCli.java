package net.tognola.zensole;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

@java.lang.SuppressWarnings({"squid:S2629", "squid:S106"})
public class ZenSoleCli {

    private final Logger log = LoggerFactory.getLogger(ZenSoleCli.class.getCanonicalName());

    static final String WELCOME = "Welcome to ZenSole - The ZenDesk Search Console Tool";

    private static final String EXIT = "EXIT";
    private static final String SEARCH_TICKETS = "tickets";

    private final SearchController searchController;
    private final ResultRenderer resultRenderer;
    private final Scanner scanner;



    public static void main(String[] args) throws IOException {
        new ZenSoleCli().loop();
    }



    ZenSoleCli() {
        this(System.in);
    }



    ZenSoleCli(InputStream inputStream) {
        scanner = new Scanner(inputStream);
        searchController = new SearchController(new ZenStore());
        resultRenderer = new ResultRenderer();
    }



    void loop() throws IOException {
        log.debug("Starting processing loop");
        print(WELCOME);
        while ((collectSearchCriteriaAndReturnSearchResult()) != EXIT)
            log.debug("Ended processing loop");
    }



    String collectSearchCriteriaAndReturnSearchResult() throws IOException {

        String entityName = promptMenuAndReturnSelectedValue("Please select the type of information to search:", new String[]{EXIT, SEARCH_TICKETS});
        if (checkForExit(entityName)) return EXIT;

        String[] searchFields = searchController.listFieldsOfEntity(entityName);
        String fieldName = promptMenuAndReturnSelectedValue("Please select the detail to search for:", searchFields);

        String fieldValue = promptForAndReturnFieldValue("Please enter the value for '" + fieldName + "' to be searched for");

        String searchResult = runSearch(entityName, fieldName, fieldValue);

        print("\nSearch Results:\n" + searchResult);
        return searchResult;
    }



    String promptMenuAndReturnSelectedValue(String title, String[] menu) {
        print("\n" + title);
        for (int i = 0; i < menu.length; i++) {
            print(String.format("%s) %s %s %s", i, ResultRenderer.ANSI_YELLOW, menu[i], ResultRenderer.ANSI_RESET));
        }
        while (true) {
            String userInput = scanner.nextLine().trim();
            if (StringUtils.isNumeric(userInput)) {
                int selectedIndex = Integer.parseInt(userInput);
                if (selectedIndex < 0 || selectedIndex >= menu.length) {
                    print("Please select a number between 0 and " + (menu.length - 1));
                }
                else {
                    print("--> You selected " + menu[selectedIndex]);
                    return menu[selectedIndex];
                }
            }
            else {
                print("Please select a number between 0 and " + (menu.length - 1));
            }
        }
    }



    String promptForAndReturnFieldValue(String title) {
        print("\n" + title);
        return scanner.nextLine().trim();
    }



    private boolean checkForExit(String menuSelection) {
        if (EXIT.equalsIgnoreCase(menuSelection)) {
            print("\nExit requested - Goodbye.");
            return true;
        }
        return false;
    }



    private void print(String s) {
        System.out.println(s);
    }



    String runSearch(String entityName, String fieldName, String fieldValue) throws IOException {
        print(String.format("Searching for %s with %s=%s...", entityName, fieldName, fieldValue));
        return resultRenderer.render(searchController.search(entityName, fieldValue));
    }


}
