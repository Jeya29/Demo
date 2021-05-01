# Spring Boot Rest Microservices
This is a **Java / Maven / Spring Boot (version 2.4.5)** application that can be used as a starter for creating a Rest Api that uses **Spring Data JPA**
to store and retrieve data in a in-memory database - **H2**.

## How to Run 

This application is packaged as a jar which has Tomcat embedded. No Tomcat installation is necessary. You run it using the ```java -jar``` command.

* Clone this repository 
* Make sure you are using JDK 1.8 and Maven 3.x
* You can build the project and run the tests by running ```mvn clean package```
* Once successfully built, you can run the service by one of these two methods:
```
        java -jar -Dspring.profiles.active=test target/candidate-details-0.0.1-SNAPSHOT.jar
or
        mvn spring-boot:run -Drun.arguments="spring.profiles.active=test"
```
Once the application runs you should see something like this

```
2021-05-01 20:44:40.601  INFO 9703 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path
2021-05-01 20:44:40.610  INFO 9703 --- [           main] com.org.candidate.CandidateApplication   : Started CandidateApplication in 5.211 seconds (JVM running for 5.561)
```

## About the Service

The service allow applicants to register their details in an organization which can be fetched or modified later using the respective Rest microservices.
It uses an in-memory database (H2) to store the data. It can be replaced with any relational database like MySQL or PostgreSQL, provided the required configuration
in the properties. REST endpoints are defined in ```com.org.controller.controller.CandidateController``` 
on **port 8080**. (see below)

Here is what this application illustrates: 

* Full integration with the latest **Spring** Framework: Spring AOP, dependency injection, etc.
* Implementation of RESTful service wrapping réponse around response entity with clear HTTP statuses for each scenario.
* Exception handling from application exceptions to the right HTTP response with exception details in the body.
* Usage of *Lombok* for generation of source to avoid boiler plate code.
* *Spring Data* Integration with JPA.
* Automatic CRUD functionality against the data source & relationship between entity classes using Spring Data *JPA Repository* pattern
* Spring AOP to print the time taken for each end-point execution & logging using Slf4j.
* Configuring *MockMVC* test framework with associated libraries & *Mockito with Junit 5* for test cases.
* Code Coverage analysis for the tests using *JACOCO Maven Plugin*.
* All APIs are "self-documented" in **Swagger UI** using open-api.

Here are some endpoints :

### Create candidate entry
```
POST /v1/candidates
Accept: application/json
Content-Type: application/json

{
  "firstName": "Alex",
  "lastName": "Smith",
  "email": "alex@gmail.com",
  "technology": "Java",
  "experience": "4 years",
  "address": {
    "address": "1, Kings street",
    "city": "Melbourne",
    "postcode": "3004",
    "country": "Australia"
  }
}
Response: HTTP 200

```
### Retrieve a paginated list of candidates

```
GET
http://localhost:8080/v1/candidates/?page=1&size=3

Response: HTTP 200
Content: paginated list 
```
### Retrieve candidates by first name

```
GET
http://localhost:8080/v1/candidates/Alex

Response: HTTP 200
Response Body:
[{
  "id":1,
  "firstName": "Alex",
  "lastName": "Smith",
  "email": "alex@gmail.com",
  "technology": "Java",
  "experience": "4 years",
  "address": {
    "address": "1, Kings street",
    "city": "Melbourne",
    "postcode": "3004",
    "country": "Australia"
  }
}]

```
### Update/Modify candidate details
```
PUT /v1/candidates/1
Accept: application/json
Content-Type: application/json

{
  "firstName": "Alex",
  "lastName": "Williams",
  "email": "alex@gmail.com",
  "technology": "Java",
  "experience": "4 years",
  "address": {
    "address": "5, Collins street",
    "city": "Melbourne",
    "postcode": "3004",
    "country": "Australia"
  }
}

Response: HTTP 200
```
### Delete a candidate by id

```
DELETE
http://localhost:8080/v1/candidates/1

Response: HTTP 200
Response Body: Candidate deleted successfully 
```
### To view Swagger 2 API docs

Run the server and browse to ```localhost:8080/swagger-ui.html```

### To view your H2 in-memory datbase

Adding ```spring.h2.console.enabled=true``` in the property file, we can view the H2 in-memory database console. To view and query the database you can browse to 
```http://localhost:8080/h2-console``` Default username is 'sa' with a blank password.






