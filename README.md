
# Spring Boot (API)
## To Run api
 - To run the api, simply open the project in InjelliJ or anyother IDE
 - Run class named "RestTestApplication" inside "rest_test/src/main/java/com.example.rest_test" file path
 
## Consuming Data
 - All business logic can be found in LogicService class in "rest_test/src/main/java/com.example.rest_test/business/service" 
 - The application runs on port 8080.
 - In order to grab data using links, use: localhost:8080/controller/{requests}..
 - Table below shows all link paths to grab data:
 
| localhost:8080/controller|                  | 
| ------------------------ |----------------------:| 
| logs by userid                  | /getLogsById/{userId} | 
| logs by type                          | /getLogsByType/{type}                      |
| logs by time and type         | /logsByTypeTime/{types}/{timeFrom}/{timeTo}|
| logs by userid and type|/logsByIdType/{userId}/{type}| |
| logs by userid and time|/logsByIdTime/{userId}/{timeFrom}/{timeTo}|
| logs by userid type and time|/logsByIdTypeTime/{userId}/{timeFrom}/{timeTo}/{type}|
| get all log objects |/getAllLogs|

 * it has to be noted that there is no post method to save to database. I could not solve issues with foreign key conflicts
 * whlie passing time as parameter, please use "2018-10-18T21:37:28-06:00" format 
 
## Database 
 - The application makes use of embidded datasource.
 - Data schema file can be found inside "rest_test/src/main/resources" file path
 
## Data
 - Out of the box, the data.sql(found in rest_test/src/main/resources) file adds 4 users.
 - Their ids are user1, user2, user3, and user4 respectively
 - user1 has two sessions (ids: session1, and session11)
 - session11 does not have "view" type in its aciton object
 - All other users have only one session, and contain all 3 types: click, view, navigate
 - Time is saved in string format, and there are only two different time variation:
    - 2018-10-18T21:37:28-06:00
    - 2018-11-18T21:37:28-07:00
 - More data can be added using data.sql file, but for every Actions object added, a Properties object with same ACT_ID needs to be added.
 - I could not make repository.save() function work due to foreign key conflicts, hence, when Actions object is added, fields in Properties object needs to be filled accordingly.
 - For each action object added, Properties with same type and ACT_ID needs to be added. Fields in properties object that needs to be filled depending on object is shown in the table below.

| Click         | View          | Navigate  |
| ------------- |:-------------:| ---------:|
| locationX     |               | pagefrom  |
| locationY     | viewedId      |  pageTo   |

## Scability questions:
 - One trick to make this api better would be to make method calls async. This way multiple calls from multiple users can be made to this api.
 - The session data could be saved to a different database instead of using local memory, so when the system resumes, other prcoess can grab the data.
 - You could also add logic that handles shutdowns, so when a shutdown request comes in, new request can be gracefully rejected while the current process is still running. 

## Few Improvements I could have made
 - Refactoring. Feels like there is a lot of repeating codes, but keeping methods separate could make it more managable down the road. 
 - Foreign key issues that affected savingToDatabase methods. Could not create new log object. 
