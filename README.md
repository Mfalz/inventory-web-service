# Inventory Service

An inventory web service running within Spring Boot.

## Prerequisites
### Create a firebase project
* Create a project and download the service account json file in `resources/firebase/admin-service-account.json`
* Configure the `application.yaml` configuration file with the right project-id (see the firebase section)

### Configure the clients
The service restricts the access to only allowed clients, you can configure allowed clients in the `application.yaml` file:
```bash
# eg. for postman client

application:
  allowedClients:
    postman:
      clientUsername: 123456
      clientPassword: password
```

## How to run
You can run the service as docker container:

```bash
mvn clean install
docker build -t inventory-service .
docker run -d -p 8080:8080 -v $(pwd)/src/main/resources/firebase:/resources:ro inventory-service
```

### Test the health endpoint
```bash
curl --location --request GET 'http://localhost:8080/actuator/health' \
--header 'client-id: postman' \
--header 'client-username: 123456' \
--header 'client-password: password' 
```