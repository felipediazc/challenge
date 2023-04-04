# BACKEND ENGINEER CODE CHALLENGE FOR ONTOP

* By Felipe Diaz C: [felipediazc@gmail.com](mailto:felipediazc@gmail.com)

Disclaimer: Please use a Markdown editor/viewer with mermaid support in order to have access to the diagrams

### SOFTWARE REQUIREMENTS

You need MAVEN 3.8.1 and JAVA 17 installed in your PC, MAC or LINUX.

### HOW TO PACKAGE

mvn clean install

### HOW TO TEST

If you want to do the unit and integration test, please use the following instruction:

mvn test

### HOW TO PACKAGE WITHOUT TESTING

mvn clean install -DskipTests

### HOW TO DOWNLOAD ALL DEPENDENCIES

mvn install dependency:copy-dependencies

## HOW TO RUN (Default port is 8080)

There is two alternatives to run de application

1. mvn spring-boot:run
2. java -jar target/challenge-0.0.1-SNAPSHOT.jar

## ENDPOINTS

http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/

## CODE COVERAGE

Please look into the target/site/jacoco folder for de index.html file

## H2 CONSOLE

In order to see data saved into the H2 database, please go to the following URL:

http://localhost:8080/h2-console/

And use the following connection parameters:

        url: jdbc:h2:mem:ontopchallengedb
        username: sa
        password: sa

## EXAMPLE DATA

The data related to bank accounts must be reusable to carry out transactions. This means that they should not be entered every time a money transfer is to be made, which is why these data are kept in a database (H2). This information is stored in the WALLETS table.
There is also a table called ACCOUNTS which stores the ONTOP banking information.

ACCOUNTS TABLE

| ID | ACCOUNT NUMBER | NAME      | ROUTING NUMBER |
|----|----------------|-----------|----------------|
| 1  | 0245253419     | ONTOP INC | 028444018      |

WALLETS TABLE

| ID | ACCOUNT NUMBER | BANK NAME | NAME  | LASTNAME | NATIONAL ID NUMBER | ROUTING NUMBER |
|----|----------------|-----------|-------|----------|--------------------|----------------|
| 1  | 1885226711     | BANK1     | TONY  | STARK    | 1111111111         | 211927207      |
| 2  |                | BANK1     | TONY  | STARK    | 1111111111         | 211927207      |
| 3  | 1885226711     | BANK1     | JAMES | FAILED   | 1111111112         | 211927207      |
| 4  | 1885226711     | BANK1     | JAMES | TIMEOUT  | 1111111113         | 211927207      |

This information is loaded by default when you start the application and corresponds to the same information that is used on the Mock server for payments.

For example, if you use accountId=1 (ONTOP) and walletId=1 (TONY STARK) to run the payment endpoint, you will get a 200 (success) response. But if you use accountId=1 and walletId=2, you will get 400 error (as in mock delivered by ontop)
The following table shows the different combinations and responses regarding the ACCOUNTS table and the WALLETS table to use with the payments endpoint.

| ACCOUNT ID | WALLET ID | RESPONSE                  |
|------------|-----------|---------------------------|
| 1          | 1         | 200 OK                    |
| 1          | 2         | 400 ERROR (body invalid)  |
| 1          | 3         | 500 ERROR (bank rejected) |
| 1          | 4         | 500 ERROR (Timeout)       |

You can use the following POSTMAN Collection to test the data [ONTOP_Challenge.postman_collection.json](./ONTOP_Challenge.postman_collection.json), also you can use the well documented [SWAGGER_UI](http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/) to do your tests.

Keep in mind that the H2 database is configured on-memory so its data will be lost every time the application is stopped. When starting it will reload the default data

When a transaction is successful (code 200), there is data that is entered into the TRANSACTIONS and ONTOPCOMMISSIONS tables. If you want to verify the existence of this data, you must enter the [H2 console](http://localhost:8080/h2-console/) with the instructions given previously.

## ABOUT THE TECHNICAL TEST

Please review the documentation contained in the [Ontop’s Challenge for backend with Java](./Ontop_Challenge_for_backend_with_Java.pdf) PDF file

## SOLUTION DESIGN

 Please go to into the [DESIGN.md](./DESIGN.md) document.