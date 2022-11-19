# Drone-Service
## Motivation
This service is created to control all tasks can be done by drone, these tasks include:
 
- Register a drone in the drone fleet.
- Load medication items on the drone.
- Deliver the loaded medication items to provided address.
- Return it back to fleet to be ready to the next trip.

## How to bootup the service

### Prerequisites:

- Docker installed on your PC.

### Steps:

 - Clone the project and open the terminal at the project directory.
 - Using the advantage of the **Make File** I have added to run the commands needed to build and run the service.

1- Firstly, run the following command to set up the environment and run the service
 `make setup`

2- After running the previous command, all the required images should be pulled and ran into containers, 
and now you can head to the **_swagger_** page I have added to play with the service and test the requirements using the following URL:
`http://localhost:8080/swagger-ui/index.html#/`

3- At swagger, all the added endpoints are documented to know the required request parameters and the expected response.


## How to run tests

- There are two kind of tests have been created:

 1- **Unit tests:** you can run them using the following command:
`make unit-test`

2- **Integration tests:** you can run them using the following command:
`make integration-test`

### Note: 
 - In case you encountered any problems running tests, make sure that your terminal pointing to 
java version 11, because there are some dependencies/plugins have been added require java version 11 to work fine.

- To switch to java 11, run the following command: 
`export JAVA_HOME= /usr/libexec/java_home -v 11.0.15`

--> add " ` " before and after this part /usr/libexec/java_home -v 11.0.15 ,  because it was misleading to add them in README, because this symbol is used in MarkDown language

- You can run the same command to switch to another java version by changing `11.0.15` to your version.
