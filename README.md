# Getting Started

### Technologies Used

* Maven
* Spring Boot web 
* Spring Data for database access layer
* In-memory database H2
* JUnit 5
* Project skeleton generated using Spring Initializer


### Assumptions & Rules
The following assumptions were made:

* One user can rent only one copy of the same book.
* Title search will match books matching full or partial titles.
* All Ids are valid - hence not doing null checks and format checking.
* Authentication layer exists and validates before controller receives request
* In-memory database H2 is case-sensitive.
* Book table has a unique constraint on ISBN column.
* Integration Tests are based on test data created by data.sql 
* Rent period is hard coded to be 14 days. 

### TODOs

* Not every class and method have test
* Some tests lack  edge cases

### Running Application 
* Unzip the contents of rest-service.zip
* Navigate to the top level director containing pom.xml
* Open terminal
* run ./mvnw spring-boot:run
* Alternatively, execute java -jar target/rest-service-0.0.1-SNAPSHOT.jar
* Test data.sql creates schema and adds some test data


### Example http resource URLS. 

#### RENTALS
* List all books http://localhost:8080/books 
* Borrow a book http://localhost:8080/borrow?userId=<USERID>&isbn=<ISBN>
* List rented books by user http://localhost:8080/rents/<USERID>
* Return book http://localhost:8080/return?userId=<USERID>&isbn=<ISBN>

#### ADMIN
* Add new book to library [HTTP POST METHOD WITH BODY]
    * http://localhost:8080/book

* Update book  [HTTP PUT METHOD WITH BODY]
  * http://localhost:8080/book
  
* Remove a book from library [HTTP DELETE METHOD]
    * http://localhost:8080/book/<BOOKID>


