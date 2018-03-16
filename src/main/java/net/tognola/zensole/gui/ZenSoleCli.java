package net.tognola.zensole.gui;

import net.tognola.zensole.controller.SearchController;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

// exclude SonarLint check for System.out because this is intended here
@java.lang.SuppressWarnings({"squid:S2629", "squid:S106"})
class ZenSoleCli {

    private final Logger log = LoggerFactory.getLogger(ZenSoleCli.class.getCanonicalName());

    static final String WELCOME = "----------------------------------------------------" +
            "Welcome to ZenSole - The ZenDesk Search Console Tool" +
            "----------------------------------------------------";

    private static final String MENUITEM_EXIT = "EXIT";
    private static final String MENUITEM_TICKETS = "tickets";
    private static final String MENUITEM_USERS = "users";
    private static final String MENUITEM_ORGANIZATIONS = "organizations";

    private final SearchController searchController;
    private final ResultRenderer resultRenderer;
    private final Scanner scanner;



    public static void main(String[] args) throws IOException {
        new ZenSoleCli().loop();
    }



    private ZenSoleCli() {
        this(System.in);
    }



    ZenSoleCli(String... userInputs) {
        this(new ByteArrayInputStream((String.join("\n", userInputs) + "\n").getBytes()));
    }



    private ZenSoleCli(InputStream consoleInputStream) {
        scanner = new Scanner(consoleInputStream);
        searchController = new SearchController();
        resultRenderer = new ResultRenderer();
        show(WELCOME);
    }



    void loop() throws IOException {

        log.debug("Starting processing loop");
        try {
            while (! MENUITEM_EXIT.equals(
                    collectSearchCriteriaAndReturnSearchResult()
            )) ;
        }
        catch (Exception t) {
            show("ZenSole experienced an internal error. Please try again or contact support. Details: %s", t.toString());
            log.info("Unexpected error: {} ({})", t.toString(), ExceptionUtils.getStackTrace(t));
        }

        log.debug("Ended processing loop");
    }



    String collectSearchCriteriaAndReturnSearchResult() throws IOException {

        String entityName = promptForEntityName();
        if (checkForExit(entityName)) return MENUITEM_EXIT;
        String fieldName = promptForFieldName(entityName);
        String fieldValue = promptForFieldValue(fieldName);

        String searchResult = runSearch(entityName, fieldName, fieldValue);

        show("%nSearch results for %s with %s=%s: %s",
                entityName,
                fieldName,
                fieldValue,
                searchResult);

        return searchResult;
    }



    private String promptForFieldValue(String fieldName) {
        return promptForAndReturnTextValue(
                String.format(
                        "Please enter the value for '%s' to be searched for - " +
                                "entering nothing here will search for items that " +
                                "have no information for %s (then press enter)",
                        fieldName,
                        fieldName));
    }



    private String promptForFieldName(String entityName) throws IOException {
        return promptMenuAndReturnSelectedValue(
                "Please select the detail to search for (then press enter):",
                searchController.listFieldsOfEntity(entityName));
    }



    private String promptForEntityName() {
        return promptMenuAndReturnSelectedValue(
                "Please select the type of information to search (then press enter):",
                new String[]{MENUITEM_EXIT, MENUITEM_TICKETS, MENUITEM_USERS, MENUITEM_ORGANIZATIONS});
    }



    String promptMenuAndReturnSelectedValue(String prompt, String[] menu) {
        show("\n" + prompt);

        for (int i = 0; i < menu.length; i++) {
            show("%s) %s %s %s",
                    i,
                    ResultRenderer.ANSI_YELLOW,
                    menu[i],
                    ResultRenderer.ANSI_RESET);
        }

        int selectedIndex = - 1;
        while (true) {
            String userInput = scanner.nextLine().trim();
            if (StringUtils.isNumeric(userInput)) {
                selectedIndex = Integer.parseInt(userInput);
                if (selectedIndex >= 0 && selectedIndex <= menu.length - 1) {
                    break;
                }
            }
            show("Please select a number between 0 and %s", (menu.length - 1));
        }

        show("--> You selected: %s\n", menu[selectedIndex]);
        log.debug("User selected '{}'", menu[selectedIndex]);
        return menu[selectedIndex];
    }



    String promptForAndReturnTextValue(String prompt) {
        show("\n" + prompt);
        String value = scanner.nextLine().trim();
        log.debug("User selected '{}'", value);
        return value;
    }



    private boolean checkForExit(String menuSelection) {
        if (MENUITEM_EXIT.equalsIgnoreCase(menuSelection)) {
            show("\nExit requested - Goodbye.");
            return true;
        }
        else {
            return false;
        }
    }



    private void show(String format, Object... values) {
        show(String.format(format, values));
    }



    private void show(String s) {
        System.out.println(s);
    }



    private String runSearch(String entityName, String fieldName, String fieldValue) throws IOException {
        return resultRenderer.render(searchController.search(entityName, fieldName, fieldValue));
    }


}
