# Transport stop counter

This project is a playground for testing the SL LineData api

## Building and running

Easiest way to build and test to project is by using docker compose.
Make sure that the ports 3000 and 8080 are available and not in use.

compose command:
```
docker compose up --build -d
```

For older versions of docker compose:
```
docker-compose up --build -d
```

You also have the option to build without docker compose.
```
docker buildx build -t david-line-data-mock ./sl-linedata-mock
docker buildx build -t david-line-data-service ./transport-stop-counter-service
docker buildx build -t david-line-data-webapp ./transport-stop-counter-webpage
```

For older versions of docker:
```
docker build -t david-line-data-mock ./sl-linedata-mock
docker build -t david-line-data-service ./transport-stop-counter-service
docker build -t david-line-data-webapp ./transport-stop-counter-webpage
```

Start images:
```
docker run --name my-transport-webpage -p 3000:3000 david-line-data-webapp
docker run --name my-transport-service -p 8080:8080 david-line-data-service
docker run --name mock david-line-data-mock
```


## Building and running without docker

### Build/Run mock

Mock requires docker for now. (Add files and config to local nginx otherwise?)

### Build/Run service

Remember to change `transport-stop-counter-service/config.yml` if you wish to not use the mock. (not recommended don't annoy the server with unnecessary requests)

```
mvn clean install
java -jar transport-stop-counter-service/target/transport-stop-counter-service-1.0-SNAPSHOT.jar server transport-stop-counter-service/config.yml
```

The service should now be listening to port 8080.

### Build/Run webapp

```
cd transport-stop-counter-webpage
npm install
npm start
```

The web application should now be available on port 3000.

### Requirements
Apache Maven(3.9.3 tested)
Java 11(OpenJDK Runtime Environment build 20.0.2+9 tested)
nodejs (v20.5.1 tested)
npm (9.8.0 tested)
nginx (nginx/1.25.2 tested)


## Stopping the services

`docker compose down` or `docker-compose down` and I leave the issue of stopping self-build services to the user.
