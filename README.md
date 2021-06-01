Test project for Electrolux company. Example of backend service to control the operation of an appliance such as
 a wash machine or an oven.
 State messages from an appliance are handled by RESTful service and saved to MySQL database. MySQL server
 should be started on port 3306 before the application run. During the application first run the necessary
 database with one table will be automatically created. 

The REST API service handles three type of HTTP requests: POST, GET and GET with id parameter.
1. POST request
    State message from an appliance should be sent to address: localhost:8091/rest
     and should have JSON raw format with three parameters:
    "state" - string format,
    "applianceType" - ENUM format (can take the values: WASH_MACHINE, OVEN, DISHWASHER),
    "serialNumber" - string format.
     
    Example of POST request:
    {
    "state" : "rinse cycle ended",
    "applianceType" : "DISHWASHER",
    "serialNumber" : "AN12381"
    }
    
2. GET request
    GET request for reading all state messages from DB should be sent to address: localhost:8091/rest
    Response format is JSON.
    
3. GET request with id parameter
    GET request for seeking and reading the state message by its id from DB should be sent to address:
    localhost:8091/rest/{id}
    Response format is JSON.
    
    Example of GET by id request:
     localhost:8091/rest/3
     where "3" is id of needed state message.
     
 ________________________________________________
 
 Main unit tests are represented in test class StateRestControllerTest.