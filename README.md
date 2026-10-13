# Insurance Management Application

Demo backend application for managing insurance-related data, built with Spring Boot and Java.

The project focuses on clean backend architecture, persistence, validation, database migrations and integration testing.

## Tech stack

- Java 17
- Spring Boot 2.7
- Spring Data JPA / Hibernate
- MySQL 8
- Flyway
- MapStruct
- Lombok
- ActiveMQ
- Testcontainers
- JUnit 5
- Maven
- Docker / Docker Compose

## Architecture

The application uses a feature-based package structure.

Main modules:

- insurance
- tariff
- user
- temporal
- common

Each feature contains its own API, DTOs, mappers, entities, repositories and services.

## Main features

- Insurance management
- Tariff management
- User management
- Insurance price calculation using Strategy pattern
- Reinsurance calculation
- Soft delete support
- Bean validation
- XML / XSD processing
- ActiveMQ messaging
- Centralized REST error handling

## Database
Database schema is managed with Flyway migrations.

```text
src/main/resources/db/migration/common
```

Development-specific seed data is separated from the common schema migrations.

Hibernate schema generation is disabled and Hibernate validates the schema created by Flyway.

## Testing
The project contains unit and integration tests.
Database integration tests use MySQL Testcontainers to run against a real MySQL instance instead of relying only on an in-memory database.
```text
Testcontainers
    ↓
MySQL
    ↓
Flyway migrations
    ↓
Hibernate schema validation
    ↓
Repository integration tests
```
## API error handling

REST errors use a standardized response format:
```json
{
  "timestamp": "2026-10-12T13:16:12.550Z",
  "status": 400,
  "error": "VALIDATION_FAILED",
  "messages": [
    "Invalid startDate is before today"
  ],
  "path": "/insurance"
}
```

## API documentation

OpenAPI documentation is generated using Springdoc OpenAPI.

After starting the application:

Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```
OpenAPI specification:
```text
http://localhost:8080/v3/api-docs
```

## Environment variables

Configure the database connection using environment variables:
```text
DB_URL
DB_USERNAME
DB_PASSWORD
ACTIVEMQ_BROKER_URL
```
## Build

```bash
mvn clean verify
```
Test conventions:
- *Test – unit and Spring slice tests
- *IT – integration tests
- integration tests use MySQL Testcontainers

## Run application locally:

```bash
mvn spring-boot:run
```
Run the Spring Boot application locally with the dev profile:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## Run the complete application stack in Docker

Build and start the application together with MySQL and ActiveMQ:
```bash
docker compose up -d --build
```
The Docker Compose environment contains:
- insurance-app – Spring Boot application
- insurance-mysql – MySQL database
- insurance-activemq – ActiveMQ broker


The application is available at:
```text
http://localhost:8080
```
ActiveMQ web console:
```text
http://localhost:8161
```

## Development profile
```bash
docker compose -f compose.yml -f compose.dev.yml up -d --build
```

## Debug mode profile
To run the development configuration with remote JVM debugging enabled:
```bash
docker compose -f compose.yml -f compose.debug.yml up -d --build
```
The debug configuration exposes the JVM debug port:
```text
5005
```
Configure IntelliJ IDEA Remote JVM Debug with:
```text
Host: localhost
Port: 5005
```

## Stop the environment

```bash
docker compose down
```
To also remove the MySQL volume and recreate the database from scratch:
```bash
docker compose down -v
```

## Purpose

This project is used to demonstrate backend development practices in Java and Spring Boot, including:
- feature-based architecture
- REST API design
- database migrations
- integration testing
- persistence with JPA/Hibernate
- validation and error handling
- common enterprise integration patterns

