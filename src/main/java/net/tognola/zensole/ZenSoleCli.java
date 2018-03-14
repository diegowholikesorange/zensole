package net.tognola.zensole;

import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.Scanner;

public class ZenSoleCli {

    private static final String EXIT = "EXIT";
    private static final String SEARCH_TICKETS = "tickets";

    private final SearchController searchController;
    private final ResultRenderer resultRenderer;
    private final Scanner scanner;



    public static void main(String[] args) {
        new ZenSoleCli().loop();
    }



    public ZenSoleCli() {
        this(System.in);
    }



    ZenSoleCli(InputStream inputStream) {
        scanner = new Scanner(inputStream);
        searchController = new SearchController(new ZenStore());
        resultRenderer = new ResultRenderer();
    }



    void loop() {
        print(welcome());

        String searchResult;
        while ((searchResult = collectSearchCriteriaAndRunSearch()) != null) {
            print("\n" + searchResult);
        }
    }



    String collectSearchCriteriaAndRunSearch() {
        String entityName = promptMenuAndReturnSelectedValue("Please select the type of information to search:", new String[]{EXIT, SEARCH_TICKETS});
        checkForExit(entityName);

        String[] searchFields = searchController.listFieldsOfEntity();
        String fieldName = promptMenuAndReturnSelectedValue("Please select the detail to search for:", searchFields);
        checkForExit(fieldName);

        String fieldValue = promptForAndReturnFieldValue("Please enter the value for '" + fieldName + "' to be searched for");

        print(String.format("Searching for %s.%s=%s", entityName, fieldName, fieldValue));
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



    private void checkForExit(String menuSelection) {
        if (EXIT.equalsIgnoreCase(menuSelection)) {
            print("Exiting now.");
            System.exit(0);
        }
    }



    private void print(String s) {
        System.out.println(s);
    }



    String welcome() {
        return "Welcome to ZenSole - The ZenDesk Search Console Tool";
    }



    String runSearch() {
        return resultRenderer.render(searchController.search());
    }


}
