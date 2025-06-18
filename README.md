# CRduels
codex/expand-readme.md-with-project-info

CRduels is a Spring Boot backend service for managing Clash Royale duel bets. It allows users to register and consult participant information.

## Building

Ensure that Maven and a Java 17 JDK are installed. To build the project run:

```bash
mvn clean package -f CrDuels/pom.xml
```

The runnable JAR will be created in `CrDuels/target`.

## Configuration

Database settings are defined in `CrDuels/src/main/resources/application.properties`. Update the PostgreSQL URL, username and password according to your environment.

```
spring.datasource.url=jdbc:postgresql://<host>:<port>/crduels
spring.datasource.username=<user>
spring.datasource.password=<password>
```

## API Usage

Start the application with:

```bash
java -jar CrDuels/target/crduels-backend-1.0.3.jar
```

Examples using `curl`:

* Register a user

```bash
curl -X POST http://localhost:8080/api/usuarios/registro \
  -H 'Content-Type: application/json' \
  -d '{"nombre":"Ejemplo","email":"ejemplo@correo.com","telefono":"+52123456789","tagClash":"#ABC123","linkAmistad":"https://link.clashroyale.com/invite/friend?tag=ABC123"}'
```

* Retrieve a user

```bash
curl http://localhost:8080/api/usuarios/<uuid>
```