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

## Run
Configure the database connection using environment variables:
```text
DB_URL
DB_USERNAME
DB_PASSWORD
```

## Build
```bash
mvn clean verify
```

Then run:
```bash
mvn spring-boot:run
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

