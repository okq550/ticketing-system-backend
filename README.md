## Ticketing System (Backend)

A Spring Boot backend for managing events, ticket types, ticket purchases, and QR-based validation. It integrates with PostgreSQL and Keycloak for persistence and authentication.

### Features
- Event management (create, update, publish)
- Ticket types and inventory control
- Ticket purchase and listing endpoints
- QR code generation and validation
- Keycloak-based authentication and authorization

### Tech Stack
- Java 17, Spring Boot
- PostgreSQL
- Keycloak (OIDC)
- MapStruct, JPA/Hibernate

### Repository Structure
- `src/main/java/org/okq550/ticketing` — application code
- `src/main/resources` — Spring configuration
- `docker-compose.yml` — local infra (Postgres, Keycloak)
- `pom.xml` — Maven build

---

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.8+
- One of:
  - Docker (or Podman/Rancher Desktop/Colima compatible with `docker-compose`)
  - Native services: PostgreSQL 14+ and Keycloak 22+

### Configuration
Default configuration is in `src/main/resources/application.properties`. If you run infra via Docker Compose, defaults should work out of the box.

Environment variables you may override (examples):
- `SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/ticketing`
- `SPRING_DATASOURCE_USERNAME=ticketing`
- `SPRING_DATASOURCE_PASSWORD=ticketing`
- `SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI=http://localhost:8080/realms/ticketing`

---

## Running Locally

### Option A: Using Docker Compose (recommended)
1. Start infra (Postgres, Keycloak):
   ```bash
   docker-compose up -d
   ```
2. Build and run the app:
   ```bash
   ./mvnw spring-boot:run
   ```

### Option B: Without Docker (native services)
1. Start PostgreSQL and Keycloak locally and configure a realm `ticketing` and a confidential client for the API.
2. Export environment variables to point Spring to your instances (see Configuration above).
3. Build and run:
   ```bash
   ./mvnw clean package
   java -jar target/ticketing-system-*.jar
   ```

The API will start on `http://localhost:8081` or the port configured in `application.properties`.

---

## API Overview

Key endpoints (non-exhaustive):
- `POST /api/events` — create event
- `PUT /api/events/{id}` — update event
- `GET /api/published-events` — list published events
- `GET /api/tickets` — list user tickets
- `POST /api/tickets` — purchase ticket
- `POST /api/tickets/validate` — validate ticket QR

See controller classes in `src/main/java/org/okq550/ticketing/controllers` for details.

---

## Development

### Tests
```bash
./mvnw test
```

### Code Style
- Follows standard Spring/Java conventions
- Prefer meaningful names and clear structure

---

## License

This project is provided as-is under the MIT License (add a `LICENSE` file if applicable).


