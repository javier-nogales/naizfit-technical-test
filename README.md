# naizfit-technical-test

## Run with Docker

```bash
./build-and-run.sh
```

This will:
- Compile the project (mvn clean package)
- Build a Docker image with Tomcat
- Deploy the .war inside the container

## Manual integration test

You can run the integration test manually like this:

```bash
mvn test -Dtest=PingEndpointIT
```
Make sure the Docker container is running (execute build-and-run.sh).