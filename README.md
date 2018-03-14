# ZenSole
Console app as code assignment for Zendesk

# Source Location
https://github.com/diegowholikesorange/zensole

# CI Location
https://circleci.com/gh/diegowholikesorange/zensole

# Assumptions
* IDs of companies, users and tickets in the provided JSON files are unique within their bounded context.
* Schema changes are likely to occur in the future
* A change of the data representation away from JSON strings to another form (XML strings, DTOs) is 
not likely (but the design should still support this)  

# Design Decisions
Based on above assumptions, I decided on and followed the below principles:
* Generic filtering by search field. The filtering of data by field should not 
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
* Avoiding domain model. There is the option to abstract the 
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
The representation of objects as JSONObjects is sufficiently typed for our purpose (KISS)
and provides a higher level of flexibility, e.g. backward compatibility in the case of
schema changes. 


 

# Incremental Delivery of Value
I implemented the solution in an iterative way, 
based on the following sequence of features:
0. End-end implementation for search of ticket by id 
(without the name of submitter, assignee and organisation included in the results)
0. Search by any field of ticket 
0. Search by any field of user
(without the name of the user's organisation included in the result) 
0. Search by any field of organisation
0. Search by any field of user, with name of organisation included in the result
0. Search by any field of ticket, with name of submitter, assignee and organisation included.  

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

### As an operations engineer I want a log file with timestamps that shows me the user input and progress of the search so that I can reproduce and analyse issues and monitor performance
#### Acceptance Criteria
* Given a search for a ticket by its id was successful 
and I now the id of the ticket that was searched 
when I open the log file
then I want the log file to contain an entry that states 
the time of the search, the ticket id and the number of results returned.
* Given a search for a ticket was unsuccessful
and I now the id of the ticket that was searched 
when I open the log file
then I want the log file to contain an entry that states 
the time of the search, the ticket id and the reason why the search failed.
* Given a search failed because of invalid search criteria (entity, field or field value), 
when I look at the corresponding log entries, 
then I want to see a message that tells me which value was incorrect and why.

