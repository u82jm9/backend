# Nonsense - Back End

A small Spring Boot back-end service for the "Nonsense" project.

This README is a lightweight framework — we can expand any section with more details, examples, and API docs as you like.

## Table of Contents

- Overview
- Tech stack
- Quick start
  - Prerequisites
  - Build
  - Run (development)
  - Run (production jar)
  - Docker
- Configuration
- Data & logs
- Tests
- Contributing
- License
- Contact

## Overview

This repository contains the back-end service for the Nonsense application. It's a Spring Boot (Java) application that serves REST endpoints and uses in-memory/runtime database for local development (H2) and JSON files under `src/main/resources` for seeded data.
The repository is solo project that I have been working on, to maintain and improve my Java coding skills. It is not intended for production use but serves as a learning and experimentation platform.
It contains a wide range of functionality, but it built in parrallel with a Front-End also written by me in the same GitHub account. The API's are designed to support the needs of that front-end application. The back-end provides endpoints for managing bike design, bike parts, notes and jokes; as well as handling log files.

## Tech stack

- Java (configured for Java 18 in `pom.xml`)
- Spring Boot 2.7.x
- Maven wrapper (`mvnw`, `mvnw.cmd`)
- H2 (runtime)
- Logging is custom built to write JSON-formatted logs to a configurable location (default: `src/main/logs/`)

## Quick start

### Prerequisites

- Java 18 (or compatible JDK)
- Git (optional)
- PowerShell (Windows) or a POSIX shell on other platforms

### Build

From the project root (Windows PowerShell):

```powershell
# run tests and build the jar
.\mvnw.cmd -DskipTests package
```

(On macOS/Linux use `./mvnw -DskipTests package`)

### Run (development)

Use the Maven Spring Boot plugin to run the app without building a jar:

```powershell
# run the app
.\mvnw.cmd spring-boot:run
```

### Run (production jar)

After packaging, run the jar:

```powershell
# build
.\mvnw.cmd -DskipTests package
# run
java -jar target\Nonsense-Java-Back-End-1.0-SNAPSHOT.jar
```

## Configuration

Configuration values are found in `src/main/resources/application.properties`. Add environment-specific overrides as needed (for example, using Spring profiles or environment variables).

## Data & logs

- Seed and sample data JSON files live in `src/main/resources/` (e.g. `bikes.json`, `notes.json`, `jokes.json`, `links.json`).
- Important documents have back-ups.
- There is a `src/main/logs/` folder in this repository containing sample log files (JSON formatted). The application may be configured to write logs to a different location depending on runtime configuration.

## Tests

Run the test suite with Maven:

```powershell
.\mvnw.cmd test
```

Test reports are output to `target/surefire-reports`.

### Test Coverage

The project currently includes the following test classes:

- All API's in the Project are tested in the Single file - `src/test/java/com/homeapp/backend/ControllerTest.java`.
- API testing checks for correct HTTP status codes, response bodies, and error handling. For example, the `getBikeById` method is tested for both existing and non-existing bike IDs.
- Each method in the controller is tested with multiple test cases, covering both successful scenarios and error handling. For example, the `addBike` method is tested with valid bike data.
- Total method coverage is around 80%, with some methods having more extensive test cases than others. The tests focus on the main functionality of the application, ensuring that the core features work as expected.

These tests cover the main functionality of the application, including edge cases like empty databases, invalid input, and non-existent resources.


## License

This project does not currently include a license file. Add a `LICENSE` or let me know which license to use and I can add it.
It is completely self built and not licenses were purchased as part of the development.

## Contact

If you need help or want specific README improvements (examples, API docs, environment variables, sample curl requests, or deployment notes), tell me which section to expand.

## Future improvements

- Expand on the bike parts available and open up more Options to give the User a more life-like representation of the possibilities when it comes to bike design.
- Improve the image carasoul for Bike parts. Making it more user friendly and visually appealing.
- Add more robust logging and error handling, with more detailed log messages and better error responses.
- Add more tests, especially for edge cases and error scenarios.
