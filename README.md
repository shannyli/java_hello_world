# Java To-Do API

A simple REST API for managing to-do items, built with Spring Boot 3.4 and Java 21.

## Endpoints

| Method   | Path           | Description         |
|----------|----------------|---------------------|
| `GET`    | `/todos`       | List all to-dos     |
| `GET`    | `/todos/{id}`  | Get a single to-do  |
| `POST`   | `/todos`       | Create a to-do      |
| `PATCH`  | `/todos/{id}`  | Update a to-do      |
| `DELETE` | `/todos/{id}`  | Delete a to-do      |

### Request/Response Examples

**Create a to-do:**

```bash
curl -X POST http://localhost:8080/todos \
  -H "Content-Type: application/json" \
  -d '{"title": "Buy groceries"}'
```

```json
{"id": 1, "title": "Buy groceries", "done": false}
```

**Mark it done:**

```bash
curl -X PATCH http://localhost:8080/todos/1 \
  -H "Content-Type: application/json" \
  -d '{"done": true}'
```

**List all to-dos:**

```bash
curl http://localhost:8080/todos
```

## Prerequisites

- OpenJDK 21

No Maven installation needed — the project includes the Maven Wrapper (`./mvnw`).

## Running the Server

```bash
./mvnw spring-boot:run
```

The server starts at `http://localhost:8080`.

## Running Tests

```bash
./mvnw test
```

## Insomnia Collection

Import `insomnia_collection.json` into Insomnia to test all endpoints. The base URL defaults to `http://localhost:8080`.

## Project Structure

```
src/main/java/com/example/todo/
  TodoApplication.java   # Spring Boot entry point
  Todo.java              # To-do model
  TodoController.java    # REST controller with all endpoints
src/test/java/com/example/todo/
  TodoControllerTest.java # Integration tests for all endpoints
```
