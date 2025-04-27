# naizfit-technical-test

## Documentation index
- :bell: [presentacion(es)](docs/presentacion_es.md)
- [technical-plan](docs/technical-test-plan.md)

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

## Project milestones

This repository uses `Git tags` to mark important development milestones.

- [`v0.1.0-infra`](https://github.com/javier-nogales/naizfit-technical-test/tree/v0.1.0-infra) — Initial infrastructure setup completed:
  - Maven project with WAR packaging
  - Dockerfile with Tomcat container and run script
  - Servlet-based API routing with Guice
  - Manual integration test (`PingEndpointIT`) included


## Scalability considerations