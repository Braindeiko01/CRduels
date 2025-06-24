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

Database settings are defined in `CrDuels/src/main/resources/application.yml`. Update the PostgreSQL URL, username and password according to your environment. Profiles `application-dev.yml` and `application-prod.yml` provide local and production defaults.

```yaml
spring:
  datasource:
    url: jdbc:postgresql://<host>:<port>/crduels
    username: <user>
    password: <password>
```

## API Usage

Start the application with:

```bash
java -jar CrDuels/target/crduels-backend-1.0.3.jar
```

Examples using `curl`:

* Register a user

```bash
curl -X POST http://localhost:8080/api/usuarios \
  -H 'Content-Type: application/json' \
  -d '{"googleId":"gid123","nombre":"Ejemplo","email":"ejemplo@correo.com","telefono":"+52123456789","tagClash":"#ABC123","linkAmistad":"https://link.clashroyale.com/invite/friend?tag=ABC123&token=XYZ"}'
```
You can include the full Clash Royale friend link, including any `token` or other parameters.

* Retrieve a user

```bash
curl http://localhost:8080/api/usuarios/<googleId>
```

* Listen for matchmaking events via SSE

```bash
curl http://localhost:8080/sse/match?jugadorId=<id>
```

The same stream is also available at `/sse/matchmaking/<id>` using a path parameter.

### API documentation

When the server is running you can explore the REST contracts using Swagger UI at:

```text
http://localhost:8080/swagger-ui/index.html
```