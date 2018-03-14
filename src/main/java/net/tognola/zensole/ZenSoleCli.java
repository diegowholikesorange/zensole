package net.tognola.zensole;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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



    public static void main(String[] args) {
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



    void loop() {
        log.debug("Starting processing loop");

        print(WELCOME);

        String searchResult;
        while ((searchResult = collectSearchCriteriaAndRunSearch()) != null) {
            print("\nSearch Results:\n" + searchResult);
        }

        log.debug("Ended processing loop");
    }



    String collectSearchCriteriaAndRunSearch() {

        String entityName = promptMenuAndReturnSelectedValue("Please select the type of information to search:", new String[]{EXIT, SEARCH_TICKETS});
        if (checkForExit(entityName)) return null;

        String[] searchFields = searchController.listFieldsOfEntity();
        String fieldName = promptMenuAndReturnSelectedValue("Please select the detail to search for:", searchFields);
        if (checkForExit(entityName)) return null;

        String fieldValue = promptForAndReturnFieldValue("Please enter the value for '" + fieldName + "' to be searched for");

        log.debug(String.format("Searching for %s.%s=%s", entityName, fieldName, fieldValue));
        return runSearch();
    }



    String promptMenuAndReturnSelectedValue(String title, String[] menu) {
        print(title);
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
                    print("Selected " + menu[selectedIndex]);
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
            print("Exit requested");
            return true;
        }
        return false;
    }



    private void print(String s) {
        System.out.println(s);
    }



    String runSearch() {
        return resultRenderer.render(searchController.search());
    }


}
