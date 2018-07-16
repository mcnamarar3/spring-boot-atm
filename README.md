# Simple Spring-Boot ATM application

This is a simple ATM application based on a REST API for user input and hard coded data (ATM balance and customer accounts).
The application utilises the Spring-Boot framework and a maven build script.

This application has been developed using codeenvy.io a cloud ide.

To install the dependencies:
````
mvn clean install
````

To run the project:
````
mvn spring-boot:run
````

To run the project tests:
````
mvn clean test
````

SwaggerUI can be used to view documentation and test the REST API here when the project is running
````
http://localhost:8080/swagger-ui.html
````

## Outstanding items

- improve test coverage
- add swagger markup to generate API docs
- add logging to the application
- refactor sample data service to make the project extensible with a real backend e.g. another service which provides access to data or a database