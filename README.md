# Patient Management Service
Web service for patient management

## How to Run
You run it using the ``java -jar`` command.
* Clone this repository 
* Make sure you are using JDK 1.8 and Maven 3.x
* You can build the project and run the tests by running ``mvn clean package``
* Once successfully built, you can run the service by :
```
java -jar target/patient-management-service-1.0.0-SNAPSHOT.jar
```
Once the application runs you should see
```
Service is running
```

## About the Service
The service is patient management REST service. It uses an in-memory database (H2) to store the data.

### To view your H2 in-memory datbase
* The 'test' profile runs on H2 in-memory database. To view and query the database you can browse to ``http://localhost:8080/h2-console``. JDBC URL is ``jdbc:h2:mem:testdb`` and default username is 'sa' with a blank password. 

### To view Swagger API docs
* Run the server and browse to ``localhost:8080/swagger-ui.html``

Here are some endpoints you can call:
### Create a patient resource
```
POST http://localhost:8080/patients

json
{
    "firstName": "Jay",
    "lastName": "Fung",
    "dob": "2000-02-14"
}

curl command:
curl --location --request POST 'localhost:8080/patients/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstName": "Jay",
    "lastName": "Fung",
    "dob": "2000-02-14"
}'
```
### Retrieve a list of all patients
```
GET http://localhost:8080/patients
Response: HTTP 200
Content: the list of all patients

curl command:
curl --location --request GET 'localhost:8080/patients'
```
### Retrieve a patient by id
```
GET http://localhost:8080/patients/1
Response: HTTP 200
Content: patient

curl command:
curl --location --request GET 'localhost:8080/patients/1'
```
### Retrieve a patient by first name
```
GET http://localhost:8080/patients?firstname=tom
GET http://localhost:8080/patients
Response: HTTP 200
Content: the list of patients matched

curl command:
curl --location --request GET 'localhost:8080/patients?firstname=tom'
```