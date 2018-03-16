# ZenSole
Console app as code assignment for Zendesk



# Build and Run
Instructions here are for Linux (and OSX).

## System Requirements
To build and run the CLI you'll need the following:

* git
* Java 8 or higher

## Build
```
git clone https://github.com/diegowholikesorange/zensole.git
cd zensole
./gradlew test
```

## Run
Run the CLI
```
./zensole.sh
```
Tail the log file
```
tail -f zensole.log
```

## Security Check
The CLI uses a few dependencies, defined in the build.gradle file.
These dependencies are checked for known vulnerabilities 
(using OWASP dependency check https://www.owasp.org/index.php/OWASP_Dependency_Check).
To perform the check yourself, run:
```
./gradlew dependencyCheckAnalyze
```

**Note:** This will trigger a download of known CVE definitions that may take a few minutes on the first run. 
The CVEs are cached for 4 hours, subsequent builds will therefore be faster.

## Source Location
https://github.com/diegowholikesorange/zensole

Main entry point is ```net.tognola.zensole.gui.ZenSoleCli```.

## CI Location
https://circleci.com/gh/diegowholikesorange/zensole

CI runs build, tests and dependency check.

# Assumptions
* IDs of companies, users and tickets in the provided JSON files are unique within their bounded context.
* Schema changes are likely to occur in the future
* A change of the data representation away from JSON strings to another form (XML strings, DTOs) is 
not likely (but the design should still support this)  
* It's ok to match by substring instead of full value too (e.g "mar" will return "mary")
* Only some values from related entities need to be included in the search results. 
These are the name of the entity referred to (organization name and user name).

# Design Decisions
Based on above assumptions, I decided on and followed the below principles:
* **Generic filtering by search field.** The filtering of data by field should not 
have long if statements checking the field name specified in the search query against
a hardcoded list of each fields of the entities (DRY). We want to avoid expressions such as: 
```   
    if (searchQuery.fieldName == 'submitter_id' && dataItem.submitter_id==searchQuery.fieldValue) then {
        searchResults.add(dataItem);
    }
    if (searchQuery.fieldName == 'assignee_id' ...
```    
This should be avoided by using a generic solution, 
e.g. maps with field names as keys or Java reflection.
* **Avoiding domain model.** There is the option to abstract the 
representation of data in JSON format away from the consumers (query and renderer). 
This could easily be achieved by introducing a domain layer with objects for Organisation,
User, Ticket implementing some "SearchResultItem" interface. 
The advantage would be that a stronger OO approach 
could be taken, e.g. by implementing the rendering logic within the entities. 
But this would work against the separation of concerns, 
e.g. for enrichment and rendering of results. 
Another disadvantage would be that the data schema is then known to the application,
which from a search perspective is not necessary at this stage (YAGNI). 
I feel that a domain layer would bloat the code while not adding much value. 
The representation of objects as JSONObjects is sufficiently structured for our purpose (KISS)
and provides a higher level of flexibility, e.g. automatic backward compatibility in the case of
(most) schema changes. 

# Incremental Delivery of Value
I implemented the solution in an iterative way, 
based on the following sequence of features:

1. End-end implementation for search of ticket by id (without the name of submitter, assignee and organisation included in the results)
1. Search by any field of ticket 
1. Search by any field of user (without the name of the user's organisation included in the result) 
1. Search by any field of organisation
1. Search by any field of ticket, with name of submitter, assignee and organisation included.  
1. Search by any field of user, with name of organisation included in the result

I started with the ticket because I assumed that there would be the highest value
in finding tickets, more than finding organisations or users.

# User Stories
### As a user I want to be able to search a ticket by its id so that I can see all details of the ticket.
#### Acceptance Criteria
* Given a ticket with id NNN exists, 
when I search for a ticket with id NNN 
then I want to see all details of the correct ticket (with id NNN).
* Given a ticket with id NNN does not exist, 
when I search for a ticket with id NNN 
then I want to be notified that no matching ticket exists.

### As a user I want to be able to search a ticket by any value for any field so that I can see all details of the ticket.
#### Acceptance Criteria
* Given tickets exist with value X for field F, where X is not empty,
when I search for tickets with value X for field F 
then I want to see all tickets that have value X for field F and I want to see all the fields of each ticket.
* Given tickets exist with empty or no value for field F,
when I search for tickets with empty value for field F 
then I want to see all tickets that have an empty or no value for field F 
and I want to see all the fields of each ticket.

### As a user I want to be able to search a user or organisations by any field value so that I can browse the Zen data.
#### Acceptance Criteria
* Given user/organization exist with value X for field F, where X is not empty,
when I search for user/organization with value X for field F 
then I want to see all user/organization that have value X for field F and I want to see all the fields of each user/organization.
* Given user/organization exist with empty or no value for field F,
when I search for user/organization with empty value for field F 
then I want to see all user/organization that have an empty or no value for field F 
and I want to see all the fields of each user/organization.

### As a user I want to see the names of organisations and users with their IDs so that I know who the ID represents
#### Acceptance Criteria
* Given search criteria exist for tickets and users that return one or more matches 
when I perform the corresponding searches and look at the results
then I want to see ID and name for any user or organisation listed in the results.
  

### As an operations engineer I want a log file with timestamps that shows me the user input and result of the search so that I can reproduce and analyse issues and monitor performance
#### Acceptance Criteria
* Given a search for a ticket by its id was successful 
and I know the id of the ticket that was searched 
when I open the log file
then I want the log file to contain an entry that states 
the time of the search, the ticket id and the number of results returned.
* Given a search for a ticket was unsuccessful
and I know the id of the ticket that was searched 
when I open the log file
then I want the log file to contain an entry that states 
the time of the search, the ticket id and the reason why the search failed.
* Given a search failed because of invalid search criteria (entity, field or field value), 
when I look at the corresponding log entries, 
then I want to see a message that tells me which value was incorrect and why.
* Given an internal, unexpected error happens in the application
when I open the log file
then I want to see the Java exception type and stacktrace for the error.

### As a user I want to be able to stop (exit) from the CLI so that I can reuse the console for other tasks.
#### Acceptance Criteria
* Given that the CLI prompts me for the entity type to be searched
when I look at the options
then I want to see an option to EXIT the CLI.
* Given that the CLI prompts me for the entity type to be searched
when I select the menu option EXIT
then I want the CLI to stop.
