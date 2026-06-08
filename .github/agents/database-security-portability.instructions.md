---
name: Database Security & Portability
description: Enforce secure persistence code and database-agnostic design so the application can switch databases later.
applyTo:
  - src/main/java/**/model/**
  - src/main/java/**/repository/**
---

This instruction applies to model and repository code in the Java Spring Boot project.

Rules:

- Use abstraction layers: prefer Spring Data JPA repository interfaces (`JpaRepository`, `CrudRepository`) and service-layer interfaces.
- Avoid direct JDBC string concatenation and vendor-specific SQL in business logic.
- Use parameterized queries and query method parameters. Never construct SQL strings from user input.
- Validate and sanitize external inputs before using them in queries.
- Keep persistence logic isolated in repositories, not in controllers or services.
- Prefer standard JPA annotations and portable entity mappings. Avoid database-specific annotations unless absolutely necessary.
- If native queries are required, isolate them in repository methods, document the database-specific dependency, and keep them minimal.
- Use Spring Boot externalized configuration for datasource settings and database dialect.
- Do not hard-code credentials, connection URLs, or vendor details in source files.
- Load secrets from environment variables, externalized properties, or secure configuration services.
- Follow the principle of least privilege for database access; use a limited DB account for the application.
- Use transaction boundaries in the service layer, not in controllers.
- Keep database type and schema migration strategy configurable so the app can support a different DB later.
- Prefer vendor-agnostic schema definitions and migration scripts (Flyway/Liquibase) over database-specific DDL.
- Document any DB-specific behavior clearly when it cannot be avoided.

Examples of what to enforce:

- Replace native SQL string construction with Spring Data query methods or `@Query` with parameters.
- Use `application.properties` or `application.yml` to configure `spring.datasource.*` and `spring.jpa.database-platform`.
- Use repository interfaces for CRUD operations and business services that depend on those interfaces.
- Encrypt or externalize any sensitive database credentials.

Suggested prompts to see this in action:

- "Refactor this repository layer to use Spring Data JPA and remove native SQL where possible."
- "Generate a database-agnostic persistence layer for Movie, Screen, Theatre, and Booking entities."
- "Update configuration so datasource credentials are externalized and the DB can be switched via Spring profiles."