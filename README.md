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
```
### Retrieve a list of all patients
```
GET http://localhost:8080/patients
```
### Retrieve a patient by id
```
GET http://localhost:8080/patients/1
```
### Retrieve a patient by first name
```
GET http://localhost:8080/patients?firstname=tom
```