# Spring Boot 4 demo: RestClient, Validation and Exception Handling

A Spring Boot 4 demo showcasing modern REST API development with external API integration, validation, and exception handling.

## What You'll Learn

This demo demonstrates:

- **RestClient Integration**: Using Spring Boot 4's HTTP interface to consume external APIs
- **Input Validation**: Bean Validation with `@Valid`, `@NotBlank`, `@Email`, `@Size`
- **Global Exception Handling**: `@RestControllerAdvice` with `ProblemDetail` responses
- **JPA & MySQL**: Database integration with Spring Data JPA
- **Docker**: Containerized MySQL database setup

## Prerequisites

- **Java 25** or higher
- **Docker & Docker Compose**
- **IntelliJ IDEA** (or any IDE)
- **OpenAI API Key** (optional - for GPT integration features)

## Getting Started

### 1. Start the Database

The application uses Docker Compose to run a MySQL 8.4 database:

```bash
docker compose up -d
```

The database will be available at `localhost:3307` with credentials:
- Database: `mydb`
- Username: `ek`
- Password: `ek`

### 2. Configure OpenAI API Key (Optional)

If you want to use the DatamatikerGPT feature, add your OpenAI API key to `application.properties`:

```properties
openai_api_key=your-api-key-here
```

Alternatively, use an environment variable:
```bash
export OPENAI_API_KEY=your-api-key-here
```

> **Note**: The application will start without an API key, but the `/api/gpt` endpoint will not work.

### 3. Run the Application

**Using IntelliJ IDEA:**
- Open the project in IntelliJ
- Run the `Application` class

The application will start at `http://localhost:8080`

## API Endpoints

### User Management (Local Database)

#### Get All Users
```http
GET /api/users
```

#### Get User by ID
```http
GET /api/users/{id}
```

#### Create User (with validation)
```http
POST /api/users
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com"
}
```

**Validation Rules:**
- `name`: Required, 2-150 characters
- `email`: Required, valid email format

#### Get User with Todos
```http
GET /api/users/{id}/todos
```
Fetches user from database and their todos from external API.

### Todos (External API - JSONPlaceholder)

#### Get All Todos
```http
GET /api/todos
```

#### Get Todo by ID
```http
GET /api/todos/{id}
```

#### Create Todo
```http
POST /api/todos
Content-Type: application/json

{
  "userId": 1,
  "title": "Learn Spring Boot",
  "completed": false
}
```

#### Delete Todo
```http
DELETE /api/todos/{id}
```

### DatamatikerGPT (OpenAI Integration)

#### Get GPT Response
```http
GET /api/gpt?prompt=Explain what a RestClient is
```

## Key Concepts Explained

### 1. RestClient with @HttpExchange

The Spring Boot 4 approach using declarative HTTP clients:

```java
@HttpExchange("/todos")
public interface TodosClient {
    @GetExchange
    List<TodoResponse> getTodos();
    
    @GetExchange("/{id}")
    TodoResponse getTodoById(@PathVariable Long id);
}
```

Configuration in `HttpConfig.java` using `RestClientHttpServiceGroupConfigurer`.

### 2. Input Validation

Using Bean Validation annotations on Records:

```java
public record CreateUserRequest(
    @NotBlank(message = "Name must not be blank")
    @Size(min = 2, max = 150)
    String name,
    
    @Email
    @NotBlank
    String email
) {}
```

Controllers use `@Valid` to trigger validation:
```java
@PostMapping
public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
    // ...
}
```

### 3. Global Exception Handling

`@RestControllerAdvice` with `ProblemDetail` (RFC 7807):

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ProblemDetail> handleNotFoundException(NotFoundException ex) {
        // Returns standardized error response
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationException(MethodArgumentNotValidException ex) {
        // Returns validation errors
    }
}
```

### 4. Database Integration

- **Entity**: `User` class with JPA annotations
- **Repository**: `UserRepository extends JpaRepository`
- **Service**: `UserService` with business logic
- **Hibernate**: Auto-creates tables with `spring.jpa.hibernate.ddl-auto=update`

## 🧪 Testing the API

### Using curl

```bash
# Create a user
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Alice","email":"alice@example.com"}'

# Get all users
curl http://localhost:8080/api/users

# Get todos
curl http://localhost:8080/api/todos

# Ask DatamatikerGPT (if API key configured)
curl "http://localhost:8080/api/gpt?prompt=What%20is%20Spring%20Boot"
```

### Using IntelliJ HTTP Client

Create a file `requests.http` in your project:

```http
### Create User
POST http://localhost:8080/api/users
Content-Type: application/json

{
  "name": "Test User",
  "email": "test@example.com"
}

### Get All Users
GET http://localhost:8080/api/users

### Get User with Todos
GET http://localhost:8080/api/users/1/todos
```

## Learning Exercises

Try these exercises to deepen your understanding:

1. **Add Validation**: Add more validation rules to `CreateUserRequest` (e.g., min age, phone number format)
2. **Custom Exceptions**: Create a `DuplicateEmailException` and handle it in `GlobalExceptionHandler`
3. **New Endpoint**: Add a `PUT /api/users/{id}` endpoint to update users
4. **Query Parameters**: Add filtering to `GET /api/users?name=John`
5. **Logging**: Add logging to track API calls in `TodosClient`
6. **Testing**: Write unit tests for `UserService` and integration tests for `UserController`

## Troubleshooting

### Database Connection Issues
- Ensure Docker is running: `docker ps`
- Check if MySQL container is up: `docker compose ps`
- Verify port 3307 is not in use: `lsof -i :3307`

### OpenAI API Errors
- Check if API key is set in `application.properties`
- Verify API key is valid at [platform.openai.com](https://platform.openai.com)
- Check logs for connection errors

### Validation Not Working
- Ensure `@Valid` annotation is present in controller
- Check that validation dependencies are in `pom.xml`
- Look for validation error messages in the response body

## Resources

- [HTTP Service Client Enhancements](https://spring.io/projects/spring-boot)
- [Bean Validation Specification](https://beanvalidation.org/)
- [RFC 7807 - Problem Details](https://tools.ietf.org/html/rfc7807)
- [JSONPlaceholder API](https://jsonplaceholder.typicode.com/)
- [OpenAI API Documentation](https://platform.openai.com/docs)
