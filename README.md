# Coding Challenge
## What's Provided
A simple [Spring Boot](https://projects.spring.io/spring-boot/) web application has been created and bootstrapped 
with data. The application contains information about all employees at a company. On application start-up, an in-memory 
Mongo database is bootstrapped with a serialized snapshot of the database. While the application runs, the data may be
accessed and mutated in the database without impacting the snapshot.

### How to Run
The application may be executed by running `gradlew bootRun`.

### How to Use
The following endpoints are available to use:
```
* CREATE
    * HTTP Method: POST 
    * URL: localhost:8080/employee
    * PAYLOAD: Employee
    * RESPONSE: Employee
* READ
    * HTTP Method: GET 
    * URL: localhost:8080/employee/{id}
    * RESPONSE: Employee
* UPDATE
    * HTTP Method: PUT 
    * URL: localhost:8080/employee/{id}
    * PAYLOAD: Employee
    * RESPONSE: Employee
```
The Employee has a JSON schema of:
```json
{
  "type":"Employee",
  "properties": {
    "employeeId": {
      "type": "string"
    },
    "firstName": {
      "type": "string"
    },
    "lastName": {
          "type": "string"
    },
    "position": {
          "type": "string"
    },
    "department": {
          "type": "string"
    },
    "directReports": {
      "type": "array",
      "items" : "string"
    }
  }
}
```
For all endpoints that require an "id" in the URL, this is the "employeeId" field.

## What to Implement
Clone or download the repository, do not fork it.

### Task 1
Create a new type, ReportingStructure, that has two properties: employee and numberOfReports.

For the field "numberOfReports", this should equal the total number of reports under a given employee. The number of 
reports is determined to be the number of directReports for an employee and all of their distinct reports. For example, 
given the following employee structure:
```
                    John Lennon
                /               \
         Paul McCartney         Ringo Starr
                               /        \
                          Pete Best     George Harrison
```
The numberOfReports for employee John Lennon (employeeId: 16a596ae-edd3-4847-99fe-c4518e82c86f) would be equal to 4. 

This new type should have a new REST endpoint created for it. This new endpoint should accept an employeeId and return 
the fully filled out ReportingStructure for the specified employeeId. The values should be computed on the fly and will 
not be persisted.

### Task 2
Create a new type, Compensation. A Compensation has the following fields: employee, salary, and effectiveDate. Create 
two new Compensation REST endpoints. One to create and one to read by employeeId. These should persist and query the 
Compensation from the persistence layer.

## Delivery
Please upload your results to a publicly accessible Git repo. Free ones are provided by Github and Bitbucket.

## Post TakeHome - Quinn's Additions
• Slightly reworked the Employee class, no longer stores direct reports as a list of Employees, instead uses an 
EmployeeId wrapper object that only contains a value for employeeId, then in the setter method in Employee it converts
that list of EmployeeIds into a list of Strings

• Created the ReportingStructure type. ReportingStructure contains an Employee and an int, with the int representing the
amount of reports underneath the given Employee. Used recursion to recursively get all the employees that would be under 
a specified employee. Also added a set that would append any unique employeeId. If the employeeId was not unique, it 
would return a 0 as no new employees would be under the specified employee. Also created a basic testcase to verify the
function produced an object with the results expected given a specified user (John Lennon in this case)

• Created the Compensation type. Compensation included a String for Employee Id, a Date object for the data, and a
double for the salary. Created a CompensationRepository and a CompensationController for persistent database storage and
rest api access respectively. Also created the CompensationService and CompensationServiceImpl to add the functionality 
needed after a rest api request was submitted for Compensation. A testcase testing the CompensationService's create and
read functions was created in CompensationServiceImplTest.java.

Compensation Endpoints
```
* CREATE
    * HTTP Method: POST 
    * URL: localhost:8080/compensation
    * PAYLOAD: Compensation
    * RESPONSE: Compensation
* READ
    * HTTP Method: GET 
    * URL: localhost:8080/compensation/{id}
    * RESPONSE: Compensation
```

• Used the Employee Id instead of the Employee object in Compensation. This was due to the fact that Employee data is
already stored in the Employee database, and it seemed redundant to have both databases be partially storing that amount
of the same data. Not only that, but since Employee would be persisted in multiple places, every time you updated the 
Employee database, you would in turn also have to update the Compensation database. As the Employee Id would not change,
that meant that if you wanted the Employee associated with a Compensation, you would just need to query the Employee 
database.