# ZenSole
Console app as code assignment for Zendesk

# Assumptions
* IDs of companies, users and tickets in the provided JSON files are unique within their bounded context.


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
### As a user I want to be able to search an organisation by its id so that I can see all details of the organisation.
#### Acceptance Criteria
* Given an organisation with id NNN exists, 
when I search for an organisation with id NNN 
then I want to see all details of the correct organisation (with id NNN).
* Given an organisation with id NNN does not exist, 
when I search for an organisation with id NNN 
then I want to be notified that no matching organisation exists.

### As an operations engineer I want a log file with timestamps that shows me the user input and progress of the search so that I can reproduce and analyse issues and monitor performance
#### Acceptance Criteria
* Given a search for a company by its id was successful 
and I now the id of the company that was searched 
when I open the log file
then I want the log file to contain an entry that states 
the time of the search, the company id and the number of results returned.
* Given a search for a company was unsuccessful
and I now the id of the company that was searched 
when I open the log file
then I want the log file to contain an entry that states 
the time of the search, the company id and the reason why the search failed.
* Given a search failed because of invalid search criteria (entity, field or field value), 
when I look at the corresponding log entries, 
then I want to see a message that tells me which value was incorrect and why.

